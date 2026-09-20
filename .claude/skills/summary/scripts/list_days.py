#!/usr/bin/env python3
"""
Liet ke cac thu muc DayN o goc project, sap xep theo thoi gian sua doi gan nhat
cua Docs/Index.md (gan nhat truoc). Dung de skill "summary" de xuat ngay dang
hoc cho nguoi dung xac nhan - KHONG tu quyet dinh thay, chi goi y.

Usage:
    python3 list_days.py [--root <project_root>]
"""
import argparse
import json
import re
from pathlib import Path


def read_topic_links(docs_dir: Path) -> list[str]:
    if not docs_dir.exists():
        return []
    return sorted(
        p.name
        for p in docs_dir.iterdir()
        if p.is_file() and p.name.lower() not in {"index.md", "index"}
    )


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--root", default=".", help="Thu muc goc project")
    args = parser.parse_args()

    root = Path(args.root).resolve()
    day_pattern = re.compile(r"^Day(\d+)$")

    days = []
    for entry in root.iterdir():
        if not entry.is_dir():
            continue
        match = day_pattern.match(entry.name)
        if not match:
            continue
        index_path = entry / "Docs" / "Index.md"
        mtime = index_path.stat().st_mtime if index_path.exists() else entry.stat().st_mtime
        days.append(
            {
                "day_number": int(match.group(1)),
                "day_name": entry.name,
                "day_dir": str(entry),
                "index_exists": index_path.exists(),
                "topic_files": read_topic_links(entry / "Docs"),
                "last_modified": mtime,
            }
        )

    days.sort(key=lambda d: d["last_modified"], reverse=True)
    print(json.dumps({"days": days}, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
