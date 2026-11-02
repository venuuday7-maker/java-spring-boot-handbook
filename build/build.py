#!/usr/bin/env python
"""Build the handbook: manuscript/*.md -> book.md, book.docx, book.pdf.

Manuscript directives (each on its own line):
  {{code:ch01/hello/HelloWorld.java}}   -> fenced code block of a real, compiled source file
  {{out:ch01/hello/HelloWorld}}         -> captured output of that program (made by verify_code.py)
  ```mermaid Figure caption text        -> diagram rendered to PNG via mermaid-cli
"""
import hashlib, os, re, subprocess, sys, pathlib

ROOT = pathlib.Path(__file__).resolve().parent.parent
MS, CODE, BUILD = ROOT / "manuscript", ROOT / "code", ROOT / "build"
IMG = BUILD / "img"
OUT = ROOT / "output"
CHROME = r"C:\Program Files\Google\Chrome\Application\chrome.exe"
IMG.mkdir(exist_ok=True); OUT.mkdir(exist_ok=True)

LANG = {".java": "java", ".xml": "xml", ".yaml": "yaml", ".yml": "yaml", ".sql": "sql",
        ".properties": "properties", ".json": "json", ".sh": "bash", ".txt": "text",
        ".md": "markdown", ".dockerfile": "dockerfile"}


def render_mermaid(src: str) -> pathlib.Path:
    h = hashlib.sha1(src.encode()).hexdigest()[:12]
    png = IMG / f"{h}.png"
    if png.exists():
        return png
    mmd = IMG / f"{h}.mmd"
    mmd.write_text(src, encoding="utf-8")
    mmdc = BUILD / "node_modules" / ".bin" / "mmdc.cmd"
    cfg = BUILD / "puppeteer.json"
    subprocess.run([str(mmdc), "-i", str(mmd), "-o", str(png), "-p", str(cfg),
                    "-s", "2", "-b", "white", "-t", "neutral"], check=True)
    return png


def expand(text: str) -> str:
    def code(m):
        rel = m.group(1)
        p = CODE / rel
        if not p.exists():
            raise SystemExit(f"missing code file: {rel}")
        lang = LANG.get(p.suffix.lower(), "text")
        body = p.read_text(encoding="utf-8").rstrip("\n")
        return f"**File: `{rel.split('/', 1)[1] if '/' in rel else rel}`**\n\n```{lang}\n{body}\n```"

    def out(m):
        p = CODE / (m.group(1) + ".out.txt")
        if not p.exists():
            raise SystemExit(f"missing captured output (run verify_code.py): {m.group(1)}")
        return "**Output**\n\n```text\n" + p.read_text(encoding="utf-8").rstrip("\n") + "\n```"

    def mer(m):
        cap, src = m.group(1).strip(), m.group(2)
        png = render_mermaid(src)
        return f"![{cap}]({png.as_posix()})"

    text = re.sub(r"^\{\{code:(.+?)\}\}\s*$", code, text, flags=re.M)
    text = re.sub(r"^\{\{out:(.+?)\}\}\s*$", out, text, flags=re.M)
    text = re.sub(r"^```mermaid[ \t]*([^\n]*)\n(.*?)^```", mer, text, flags=re.M | re.S)
    return text


def main():
    files = sorted(MS.glob("*.md"))
    combined = "\n\n".join(expand(f.read_text(encoding="utf-8")) for f in files)
    book_md = OUT / "JavaSpringBootMastery.md"
    book_md.write_text(combined, encoding="utf-8")
    print(f"markdown: {book_md} ({len(combined.split())} words, {len(files)} files)")

    import pypandoc
    pandoc = pypandoc.get_pandoc_path()
    lua = str(BUILD / "callouts.lua")
    common = [pandoc, str(book_md), "-f", "markdown+fenced_divs+pipe_tables+backtick_code_blocks",
              "--lua-filter", lua, "--toc", "--toc-depth=2", "--metadata", "toc-title=Contents", "--syntax-highlighting=tango",
              "--resource-path", str(ROOT)]

    html = OUT / "JavaSpringBootMastery.html"
    pdf = OUT / "JavaSpringBootMastery.pdf"
    toc_html = BUILD / "toc.html"
    common_html = [c for c in common if c not in ("--toc",)]
    # remove "--toc-depth=2" pair since the TOC is generated here
    def make_html():
        subprocess.run([a for a in common_html if not a.startswith("--toc-depth")] +
                       ["-s", "-t", "html5", "--embed-resources", "--css", str(BUILD / "book.css"),
                        "--metadata", "pagetitle=Java & Spring Boot Mastery", "-H", str(BUILD / "head.html"),
                        "--include-before-body", str(BUILD / "titlepage.html"),
                        "--include-before-body", str(toc_html), "-o", str(html)], check=True)

    def heads():
        h = html.read_text(encoding="utf-8")
        body = h[h.index('<section class="toc">'):]
        out = []
        for m in re.finditer(r'<h([12])\b[^>]*\bid="([^"]+)"[^>]*>(.*?)</h\1>', body, re.S):
            lvl, hid, txt = int(m.group(1)), m.group(2), re.sub(r"<[^>]+>", "", m.group(3))
            txt = re.sub(r"\s+", " ", txt).strip()
            if hid != "contents":
                out.append((lvl, hid, txt))
        return out

    def write_toc(entries, pages):
        rows = []
        for lvl, hid, txt in entries:
            pg = pages.get(hid, "")
            rows.append(f'<div class="toc-row l{lvl}"><a href="#{hid}"><span class="t">{txt}</span>'
                        f'<span class="d"></span><span class="n">{pg}</span></a></div>')
        toc_html.write_text('<section class="toc"><h2 class="toc-title" id="contents">Contents</h2>\n'
                            + "\n".join(rows) + "</section>\n", encoding="utf-8")

    def run_paged():
        if pdf.exists():
            pdf.unlink()
        env = dict(os.environ, PUPPETEER_EXECUTABLE_PATH=CHROME)
        subprocess.run([str(BUILD / "node_modules" / ".bin" / "pagedjs-cli.cmd"), str(html), "-o", str(pdf),
                        "--timeout", "1200000"], check=True, timeout=1800, env=env)

    def locate(entries):
        import pymupdf
        doc = pymupdf.open(str(pdf))
        norm = lambda t: re.sub(r"\s+", " ", t)
        texts = [norm(pg.get_text()) for pg in doc]
        toc_end = max((i for i, t in enumerate(texts) if t[:40].strip().replace(" ", "").find("Contents") >= 0
                       or "Contents" in t[:25]), default=1)
        pages, cur = {}, toc_end + 1
        for lvl, hid, txt in entries:
            key = norm(txt)
            found = next((i for i in range(cur, len(texts)) if key in texts[i]), None)
            if found is None:
                print("WARN: heading not located:", txt)
                continue
            pages[hid] = found + 1
            cur = found
        return pages

    write_toc([], {})
    make_html()
    entries = heads()
    write_toc(entries, {})
    make_html(); run_paged()
    pages = locate(entries)
    for attempt in range(3):
        write_toc(entries, pages)
        make_html(); run_paged()
        new = locate(entries)
        if new == pages:
            break
        pages = new
    print("toc entries:", len(entries), "located:", len(pages))

    docx = OUT / "JavaSpringBootMastery.docx"
    subprocess.run(common + ["-t", "docx", "--metadata", "title=Java & Spring Boot Mastery: The Complete Developer Handbook",
                             "--metadata", "subtitle=Zero to Advanced", "-o", str(docx)], check=True)
    print("docx:", docx)

    from pypdf import PdfReader
    print("pdf:", pdf, "pages:", len(PdfReader(str(pdf)).pages))


if __name__ == "__main__":
    main()
