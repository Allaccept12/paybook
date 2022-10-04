drop table if exists token;
drop table if exists financial_ledger;
drop table if exists member;

create table member(
    member_id bigint not null auto_increment ,
    email varchar(255) not null,
    password varchar(255) not null,
    primary key(member_id)
)character set utf8mb4;

create table token (
        token_id bigint not null auto_increment,
        member_id bigint not null,
        token_value varchar(255) not null,
        token_expire bigint not null,
        primary key (token_id)
)character set utf8mb4;

create table financial_ledger(
    financial_ledger_id bigint not null auto_increment,
    memo varchar(255) not null default '금액 내용',
    amount int not null default 0,
    delete_flag varchar(10) not null default 'F',
    member_id bigint not null,
    primary key (financial_ledger_id)
) character set utf8mb4;

alter table token
    add constraint foreign key (member_id)
    references member (member_id);

alter table financial_ledger
    add constraint foreign key (member_id)
        references member (member_id);

