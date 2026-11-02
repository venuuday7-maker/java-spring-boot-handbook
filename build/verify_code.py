#!/usr/bin/env python
"""Compile and run every example under code/chNN/<example>/ and capture output.

Each example directory holds one or more .java files (single compilation unit set).
For every class with `public static void main`, the program runs (stdin from <Class>.in if present)
and stdout+stderr go to <dir>/<Class>.out.txt. A failed compile or a non-zero exit is reported.
Use --only <substring> to restrict.
"""
import os, pathlib, re, shutil, subprocess, sys, tempfile

ROOT = pathlib.Path(__file__).resolve().parent.parent
CODE = ROOT / "code"
JDK = next((ROOT / "tools" / "jdk").iterdir())
BIN = JDK / "bin"
only = sys.argv[sys.argv.index("--only") + 1] if "--only" in sys.argv else None

failures, ran = [], 0
for d in sorted(p for p in CODE.glob("ch*/*") if p.is_dir() and not p.name.startswith("_")):
    if only and only not in str(d):
        continue
    if (d / "pom.xml").exists():
        continue  # Maven projects are verified by verify_maven.py
    srcs = list(d.rglob("*.java"))
    if not srcs:
        continue
    tmp = pathlib.Path(tempfile.mkdtemp())
    r = subprocess.run([str(BIN / "javac"), "-Xlint:all", "-d", str(tmp), *map(str, srcs)],
                       capture_output=True, text=True)
    if r.returncode != 0:
        failures.append((d, "COMPILE", r.stderr)); shutil.rmtree(tmp, True); continue
    for s in srcs:
        text = s.read_text(encoding="utf-8")
        if "public static void main" not in text:
            continue
        pkg = re.search(r"^package\s+([\w.]+);", text, re.M)
        cls = (pkg.group(1) + "." if pkg else "") + s.stem
        stdin_f = s.with_suffix(".in")
        r = subprocess.run([str(BIN / "java"), "-cp", str(tmp), cls], capture_output=True, text=True,
                           input=stdin_f.read_text() if stdin_f.exists() else "", timeout=60)
        ran += 1
        (s.with_suffix(".out.txt")).write_text(r.stdout + r.stderr, encoding="utf-8")
        if r.returncode != 0 and not (s.with_suffix(".expect_fail")).exists():
            failures.append((d, f"RUN {cls} exit={r.returncode}", r.stderr))
    shutil.rmtree(tmp, True)

print(f"programs run: {ran}")
if failures:
    for d, kind, msg in failures:
        print(f"FAIL {d.relative_to(CODE)} [{kind}]\n{msg}")
    sys.exit(1)
print("all examples compiled (javac -Xlint:all) and ran successfully")
