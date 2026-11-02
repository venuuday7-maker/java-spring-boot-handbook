import pathlib
p = pathlib.Path(__file__).with_name("build.py")
lines = p.read_text(encoding="utf-8").split("\n")
new = r'''        for m in re.finditer(r'<h([12])\b[^>]*\bid="([^"]+)"[^>]*>(.*?)</h\1>', body, re.S):'''
for i, l in enumerate(lines):
    if "re.finditer(" in l and "body, re.S" in l:
        lines[i] = new
        print("fixed line", i + 1)
p.write_text("\n".join(lines), encoding="utf-8")
