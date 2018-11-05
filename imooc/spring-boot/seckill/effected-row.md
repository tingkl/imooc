# mybatis update 返回值为影响行数，还是实际修改行数的问题

'1', 'tingkl', '123456'
'2', 'tingkl', '2'

update user set password = '123456' where name = tingkl

匹配到2行数据， 修改了1条

mybatis返回2


'1', 'tingkl', '123456'
'2', 'tingkl', '123456'

update user set password = '123456' where name = tingkl

匹配到2行数据， 修改了1条

mybatis返回0

如何让mybatis的update返回修改的条数，加入seAffectedRows=true

spring.datasource.url=jdbc:mysql://47.104.131.31:3306/miaosha?useAffectedRows=true&useUnicode=true&characterEncoding=UTF-8
