select * from tbProductos;

alter table tbProductos add uuid varchar2(50)

update tbProductos set uuid = SYS_GUID();

commit