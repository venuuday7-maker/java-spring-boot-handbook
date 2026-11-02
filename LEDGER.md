# Production ledger — Java & Spring Boot Mastery

Target: ~800 pages (min 500 substantive). Measured density at 10pt A4: ~316 words/page (mixed prose/code/diagrams).

## Toolchain (all under C:\Users\navya\JavaBook)
- JDK Temurin 21.0.12 + Maven 3.9.16 in tools/ (portable, not on system PATH)
- Spring Boot target: 4.1.1 (latest GA on Maven Central, checked 2026-10-17; 4.2.0 is milestone only)
- Build: `python build/build.py` (needs JDK+node on PATH; see run command in session) -> output/*.md, .docx, .pdf
- Code check: `python build/verify_code.py` compiles (javac -Xlint:all) and runs code/chNN/<example>/*.java, captures *.out.txt
- Manuscript directives: {{code:path}}, {{out:path-without-ext}}, ```mermaid <caption>

## Chapter status
| Ch | Title | Budget pp | Actual pp | Status |
|---|---|---|---|---|
| FM | Front matter | - | 5 | done |
| 1 | Introduction to Programming | 6 | 15 | written, built, 4 programs verified |
| 2-55, P1-P8 | - | - | - | not started |

## Defect log
- (none yet)
