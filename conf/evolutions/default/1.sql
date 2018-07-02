# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table supply (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_supply primary key (id))
;

create sequence supply_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists supply;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists supply_seq;

