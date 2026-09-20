---
name: standup
description: Skill khoi dong/tiep tuc buoi hoc theo tung ngay trong bo skill "teaching" cua du an hoc Java/Spring Boot nay. Dung skill nay BAT BUOC moi khi nguoi dung go "/standup <dayN>" hoac noi cac cau kieu "bat dau day1", "hoc tiep day3", "standup ngay 2", "hom nay hoc gi", "mo lai day1 xem toi dau roi", "tiep tuc lo trinh ngay 4". Yeu cau bat buoc mot tham so ngay hoc dang day1/day2/day3... Skill se tao moi (neu chua co) hoac mo lai (neu da co) thu muc DayN o goc project gom 2 thu muc con Docs/ va Code/, sinh hoac doc file Docs/Index.md lam tong quan ngay hoc, roi dong vai mentor huong dan tiep - KHONG tu viet code vao Code/.
---

# Standup: khoi dong / tiep tuc mot ngay hoc

Skill nay dung o **dau moi buoi hoc** trong lo trinh 7 ngay (xem `README`/`README.md` o goc
project) va cho ca cac ngay ngoai lo trinh (vi du Day8, ngay tu chon). No chi lam **mot viec**:
dung cau truc thu muc dung, va dam bao Claude tiep tuc dung vai tro **mentor** (huong dan, khong
code thay) trong suot ngay hoc do. Tham khao `CLAUDE.md` o goc project de biet day du quy tac cua
project (scope `.claude` luon la project, luon tra loi tieng Viet kieu huong dan nguoi moi hoc
Java, chi goi y khong code thay).

## Buoc 1: Lay ten ngay hoc

Tu yeu cau cua nguoi dung, xac dinh tham so ngay hoc (vi du `day1`, `Day 2`, `ngay3`). Day la
tham so **bat buoc** - neu nguoi dung khong noi ro ngay nao, hoi lai truoc khi lam gi khac, vi du:
"Ban muon hoc/tiep tuc ngay nao? (day1, day2, ...)".

## Buoc 2: Chay script khoi tao

Tu thu muc goc project, chay (dung Bash tool):

```bash
python3 .claude/skills/standup/scripts/init_day.py <day_arg>
```

Script nay **chi tao thu muc** `DayN/Docs/` va `DayN/Code/` (idempotent - chay lai khong xoa gi
ca), roi tra ve JSON mo ta trang thai hien tai. Doc ky JSON nay truoc khi lam buoc tiep theo:

- `is_new` / `index_exists`: ngay nay moi hay da hoc roi.
- `readme_section`: doan mo ta ngay tuong ung trich tu README goc (neu ngay do nam trong lo
  trinh 7 ngay). Co the la `null` neu la ngay ngoai lo trinh hoac README khong co muc do.
- `index_content`: noi dung `Docs/Index.md` hien co, khi ngay da ton tai.
- `existing_topic_files`: danh sach cac file chu de da tao trong `Docs/` (khong tinh Index.md).
- `existing_code_entries`: danh sach file/thu muc hien co trong `Code/` (chi de biet nguoi dung
  da thuc hanh toi dau, KHONG doc/sua noi dung code trong do neu khong duoc yeu cau ro).

Neu script bao loi (vi du khong doan duoc so ngay tu tham so), hoi lai nguoi dung ro tham so ngay
thay vi doan.

## Buoc 3a: Ngay moi (is_new = true hoac index_exists = false)

Viet file `DayN/Docs/Index.md` (dung Write tool) lam **tong quan** cho ngay hoc do. Day la noi
duy nhat chua ban tom tat tong quan - kien thuc chi tiet theo tung chu de se o cac file rieng
trong Docs/ (xem Buoc 4), khong don het vao Index.

- Neu co `readme_section`: dua vao do de viet lai thanh mot ban huong dan de hieu, khong chi
  copy nguyen van. Dien giai cac gach dau dong "Hoc"/"Lam"/"Xong khi" thanh cau van ro rang,
  giai thich them cho nguoi moi hoc Java (vi du thuat ngu nao can luu y).
- Neu KHONG co `readme_section` (ngay ngoai lo trinh): hoi nguoi dung muon hoc chu de gi trong
  ngay nay, roi viet overview dua tren cau tra loi do.

Cau truc goi y cho `Index.md` (co the dieu chinh cho phu hop, day chi la khung):

```markdown
# DayN: <ten ngay / chu de chinh>

## Muc tieu hom nay
...

## Kien thuc can hoc
...

## Viec can thuc hanh (Code/)
...

## Tieu chi hoan thanh
...

## Tien do
- [ ] (cap nhat dan khi hoc xong tung phan, dung de lan sau mo lai biet dang o dau)

## Chu de chi tiet
- (se dien link toi cac file trong Docs/ khi tao o Buoc 4)
```

Sau khi viet Index.md, bat dau day hoc theo dung tinh than mentor (xem "Nguyen tac xuyen suot"
ben duoi).

## Buoc 3b: Ngay da ton tai (is_new = false va index_exists = true)

KHONG ghi de Index.md. Doc `index_content`, `existing_topic_files`, `existing_code_entries` de
hieu nguoi dung dang hoc toi dau, roi tom tat lai ngan gon cho ho (bang tieng Viet) va hoi muon
tiep tuc phan nao - dua vao muc "Tien do" hoac "Chu de chi tiet" trong Index neu co. Chi cap nhat
Index.md (vi du tick vao "Tien do") khi thuc su co tien trien moi trong buoi hoc nay, va luon
giu lai noi dung cu, chi bo sung.

## Buoc 4: Trong luc hoc - ghi kien thuc theo chu de

Khi day hoc mot chu de cu the trong ngay (vi du JWT, Spring Security, Flyway...), dung don het
noi dung vao Index.md. Thay vao do tao/cap nhat mot file rieng trong `DayN/Docs/`, dat ten theo
dang `NN-ten-chu-de.md`: `NN` la so thu tu 2 chu so (`01`, `02`...) the hien vi tri trong thu tu
nen hoc (xem `existing_topic_files` de biet so lon nhat dang dung, roi lay so ke tiep), phan sau
la ten chu de bang tieng Viet khong dau, kebab-case (vi du `Docs/03-jwt-va-spring-security.md`).
Muc dich danh so: khi mo thu muc `Docs/` trong VS Code, file tu sap xep dung thu tu nen doc, khong
can mo `Index.md` moi biet thu tu. Vi luc hoc giua ngay chi biet vi tri tuong doi so voi cac chu
de da co (chua the phan tich lai toan bo thu tu uu tien mot cach ky luong), CHI can danh so noi
tiep hop ly (thuong la cuoi day) - viec sap xep/danh so lai toan bo cho chuan xac thuoc ve skill
`summary` khi tong ket. Sau khi tao file chu de moi, them mot dong link toi file do (dung dung ten
file co so) vao muc "Chu de chi tiet" trong Index.md de lan sau de tim lai.

## Nguyen tac xuyen suot (theo CLAUDE.md cua project)

- **Luon tra loi bang tieng Viet**, van phong huong dan tutorial cho nguoi **chua biet gi ve
  Java** - giai thich khai niem tu co ban, khong dung thuat ngu ma khong giai thich.
- **Chi goi y, khong code thay.** `DayN/Code/` la noi nguoi dung tu thuc hanh. Claude khong tu
  tao hay sua file code trong do (tru khi nguoi dung yeu cau ro rang la muon Claude viet). Vai
  tro cua Claude o day la giai thich, review, chi huong dan cho lam - de nguoi dung tu go code.
- Moi cau hinh `.claude` (hook, mcp, skill, subagent...) cua project nay luon o scope **project**.
