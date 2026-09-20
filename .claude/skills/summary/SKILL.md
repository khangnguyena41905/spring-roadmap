---
name: summary
description: Skill tong ket buoi hoc trong bo skill "teaching" cua du an hoc Java/Spring Boot nay - dung cap doi voi skill "standup". Dung skill nay BAT BUOC khi nguoi dung go "/summary" hoac noi cac cau kieu "tong ket lai hom nay", "recap kien thuc vua hoc", "chot lai noi dung buoi hoc", "luu lai nhung gi vua hoc vao docs", "summary lai di". Skill se: hoi xac nhan dang hoc ngay nao, gom nhung gi vua trao doi trong phien thanh bang theo chu de va SAP XEP LAI theo thu tu nen hoc truoc-sau (khong phai thu tu noi chuyen), cho nguoi dung duyet/sua bang do, roi CHI SAU KHI nguoi dung xac nhan khong con gop y moi ghi vao Docs/ cua DayN va de nghi nguoi dung go /compact de giai phong context.
---

# Summary: tong ket va luu kien thuc vua hoc vao Docs

Skill nay dung o **cuoi mot doan hoc** (khong nhat thiet cuoi ngay) de bien noi dung vua trao doi
trong phien chat thanh tai lieu co cau truc trong `DayN/Docs/`, theo dung cau truc thu muc ma
skill `standup` da dung (doc lai SKILL.md cua `standup` neu can nho quy uoc dat ten file chu de).
Muc dich: doc lai `Docs/` sau nay phai de hieu ngay, va giai phong bot context cua phien chat sau
khi da luu.

## Buoc 1: Xac dinh va xac nhan ngay dang hoc

Chay script de goi y ngay gan hoat dong nhat (KHONG dung ket qua nay de tu quyet, chi de goi y):

```bash
python3 .claude/skills/summary/scripts/list_days.py
```

Ket qua la danh sach cac `DayN` da ton tai, sap theo thoi gian sua `Docs/Index.md` gan nhat.
Ket hop voi ngu canh hoi thoai (vi du phien nay vua chay `/standup dayN`) de doan ngay co the
dung, nhung **luon hoi lai nguoi dung de xac nhan truoc khi lam gi tiep**, vi du: "Ban dang hoc
Day1 - dung khong?". Neu nguoi dung sua lai thanh ngay khac, dung dung ngay do cho cac buoc sau.
Neu khong co DayN nao ton tai, bao cho nguoi dung biet va goi y chay `/standup <dayN>` truoc.

## Buoc 2: Gom noi dung da trao doi theo chu de

Nhin lai phan hoi thoai trong phien hien tai ke tu luc bat dau hoc ngay do (khong phai doc file -
day la noi dung dang co san trong ngu canh chat), gom thanh cac chu de kien thuc ro rang. Moi chu
de la mot cum kien thuc lien quan chat che (vi du "quy uoc dat ten trong Java", "cach viet mot
method/ham", "JWT la gi va dung the nao"...).

## Buoc 3: Sap xep lai theo thu tu nen hoc (KHONG phai thu tu da noi chuyen)

Day la phan quan trong nhat cua skill nay. Trong luc hoc, cac chu de co the duoc hoi/dap khong
theo thu tu hop ly (nguoi hoc hoi lan xon, hoi lai, nhay qua nhay lai). Khi tong ket de luu vao
Docs, hay sap xep lai theo **thu tu phu thuoc kien thuc**: cai gi la nen tang, can biet truoc, thi
dua len truoc; cai gi la xay dung tren cai khac thi dua xuong sau. Vi du: phai hieu "bien la gi,
quy uoc dat ten bien" truoc khi hoc "viet mot function/method" vi function dung bien lam tham so.
Muc dich la de sau nay doc lai Docs theo dung thu tu do se hoc duoc mach lac, khong bi nguoc.

## Buoc 4: Trinh bay bang tong ket de nguoi dung duyet (chua ghi file)

Hien bang nay ngay trong chat (markdown table), CHUA ghi vao file:

| STT | Chu de | Noi dung chinh da trao doi | File docs |
| --- | --- | --- | --- |
| 1 | ... | tom tat 1-2 cau | `Docs/<ten-file>.md` (moi / da co, se bo sung) |
| 2 | ... | ... | ... |

Cot STT the hien dung thu tu nen hoc da sap xep o Buoc 3. Sau bang, hoi ro: "Bang nay da dung
chua, ban co muon sua thu tu, gop/tach chu de, hay bo sung/bo bot gi khong?"

## Buoc 5: Vong lap chinh sua

Neu nguoi dung yeu cau sua (doi thu tu, gop/tach chu de, sua noi dung tom tat...), cap nhat lai
bang va hoi xac nhan lai (quay lai Buoc 4). Lap lai cho den khi nguoi dung xac nhan khong con gop
y gi them - **chi sang Buoc 6 khi co xac nhan ro rang**, khong tu suy dien la da dong y.

## Buoc 6: Ghi vao Docs va de nghi nen /compact

Chi thuc hien buoc nay sau khi nguoi dung da xac nhan bang tong ket o Buoc 4/5 la dung, khong con
chinh sua:

1. Dat/sua ten file theo dung quy uoc `NN-ten-chu-de.md` (giong skill `standup`): `NN` la so thu
   tu 2 chu so phan anh **dung vi tri trong bang STT vua chot**, phan sau la ten chu de tieng Viet
   khong dau, kebab-case (vi du `Docs/03-quy-uoc-dat-ten-bien.md`). Vi buoc nay la lan chot thu tu
   day du va chinh xac nhat (khac voi luc `standup` chi danh so tam o cuoi trong ngay), **doi lai
   so cho TAT CA file chu de trong `DayN/Docs/`** (ke ca file da co tu truoc, kho khan tao boi
   `standup` giua ngay hoac tu lan `summary` truoc) cho khop voi vi tri STT moi nhat - doi ten file
   (vi du dung lenh `mv` qua Bash) chi la doi ten, khong dong den noi dung ben trong nen an toan.
   Voi tung chu de: tao file moi (`Docs/<NN>-<ten>.md`) hoac neu chu de da co file roi thi **bo
   sung** noi dung moi vao cuoi hoac muc phu hop (khong xoa noi dung cu), roi doi ten file cho dung
   so moi neu vi tri cua no thay doi.
2. Cap nhat `DayN/Docs/Index.md`:
   - Muc "Chu de chi tiet": liet ke link toi tung file (dung ten file co so `NN-` moi) theo **dung
     thu tu nen hoc** da chot.
   - Ra soat cac file `.md` khac trong `Docs/` co link toi nhau (chinh no hoac file khac) bang ten
     cu - neu co, cap nhat lai link cho khop ten file moi, tranh link chet.
   - Muc "Tien do": chi tick vao nhung dong ro rang da hoan thanh dung voi noi dung vua tong ket;
     khong tu suy doan tick nhung gi chua chac chan.
3. Bao cho nguoi dung biet da luu xong (neu file nao, tao moi hay bo sung), roi **de nghi ho tu go
   lenh `/compact`** de nen bot lich su hoi thoai trong phien, giai phong khong gian context. Giai
   thich ngan gon ly do (noi dung quan trong da duoc luu vao Docs roi nen giu nguyen trong context
   la khong can thiet nua) - luu y Claude KHONG the tu kich hoat lenh nay, chi nguoi dung moi go
   duoc lenh CLI nay.

## Nguyen tac xuyen suot (theo CLAUDE.md cua project)

- Luon tra loi bang tieng Viet, van phong huong dan cho nguoi moi hoc Java.
- Khong tu sua/tao code trong `DayN/Code/` - skill nay chi dong cham toi `Docs/`.
- Khong bao gio ghi file Docs khi chua co xac nhan ro rang tu nguoi dung o Buoc 5.
