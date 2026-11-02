# Java & Spring Boot Mastery (work in progress)

A learn-from-zero handbook and interview-prep resource for Java 21 and Spring Boot 4.1, written chapter by chapter. Target: ~800 pages in Markdown, DOCX and PDF.

**Status:** Chapter 1 written and built; later chapters in progress. See `LEDGER.md`.

## What makes it different
- Every Java example is a real file under `code/`, compiled with `javac -Xlint:all` and run by `build/verify_code.py`. The program output printed in the book is the genuine captured output.
- One pipeline (`build/build.py`) produces Markdown, DOCX and PDF from the same manuscript.

## Layout
`manuscript/` chapters · `code/` verified examples · `build/` build and verification scripts · `output/` generated book
