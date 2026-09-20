#!/usr/bin/env python3
"""
Khoi tao hoac tiep tuc thu muc hoc cho mot ngay (DayN) trong lo trinh o README.md
goc project. In ra JSON de skill "standup" doc va quyet dinh buoc tiep theo.

Usage:
    python3 init_day.py <day_arg> [--root <project_root>]

<day_arg> co the la: "day1", "Day 1", "ngay1", "1", ...
Script chi tao THU MUC (Docs/, Code/), KHONG tu sinh noi dung Index.md va
KHONG bao gio tao file trong Code/ - viec do do skill/Claude dam nhan sau khi
doc JSON nay, dung tinh than "chi huong dan, khong code thay" cua du an.
"""
import argparse
import json
import re
import sys
from pathlib import Path


def parse_day_number(raw: str) -> int:
    match = re.search(r"\d+", raw)
    if not match:
        raise ValueError(
            f"Khong tim thay so ngay trong '{raw}'. Vi du hop le: day1, Day 2, ngay3, 4."
        )
    return int(match.group())


def extract_readme_section(readme_text: str, day_number: int) -> str | None:
    """Trich doan '## Ngay {day_number}...' toi truoc header '## ' ke tiep."""
    pattern = re.compile(
        rf"^##\s*Ng[aà]y\s*{day_number}\b.*?(?=^##\s|\Z)",
        re.MULTILINE | re.DOTALL | re.IGNORECASE,
    )
    match = pattern.search(readme_text)
    if not match:
        return None
    return match.group().strip()


def list_topic_files(docs_dir: Path) -> list[str]:
    if not docs_dir.exists():
        return []
    return sorted(
        p.name
        for p in docs_dir.iterdir()
        if p.is_file() and p.name.lower() not in {"index.md", "index"}
    )


def list_code_entries(code_dir: Path) -> list[str]:
    if not code_dir.exists():
        return []
    return sorted(p.name for p in code_dir.iterdir())


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("day_arg", help="Ten ngay hoc, vi du: day1, day2, ngay3")
    parser.add_argument(
        "--root",
        default=".",
        help="Thu muc goc cua project (mac dinh: thu muc hien tai)",
    )
    args = parser.parse_args()

    root = Path(args.root).resolve()

    try:
        day_number = parse_day_number(args.day_arg)
    except ValueError as exc:
        print(json.dumps({"error": str(exc)}, ensure_ascii=False))
        return 1

    day_name = f"Day{day_number}"
    day_dir = root / day_name
    docs_dir = day_dir / "Docs"
    code_dir = day_dir / "Code"
    index_path = docs_dir / "Index.md"

    is_new = not day_dir.exists()

    docs_dir.mkdir(parents=True, exist_ok=True)
    code_dir.mkdir(parents=True, exist_ok=True)

    result = {
        "day_number": day_number,
        "day_name": day_name,
        "day_dir": str(day_dir),
        "docs_dir": str(docs_dir),
        "code_dir": str(code_dir),
        "index_path": str(index_path),
        "is_new": is_new,
        "index_exists": index_path.exists(),
    }

    if is_new or not index_path.exists():
        readme_path = root / "README.md"
        if not readme_path.exists():
            readme_path = root / "README"
        readme_section = None
        if readme_path.exists():
            readme_section = extract_readme_section(
                readme_path.read_text(encoding="utf-8"), day_number
            )
        result["readme_section"] = readme_section
        result["existing_topic_files"] = []
        result["existing_code_entries"] = []
        result["index_content"] = None
    else:
        result["readme_section"] = None
        result["existing_topic_files"] = list_topic_files(docs_dir)
        result["existing_code_entries"] = list_code_entries(code_dir)
        result["index_content"] = index_path.read_text(encoding="utf-8")

    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    sys.exit(main())
