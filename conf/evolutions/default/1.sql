# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table products (
  id                        bigint not null,
  name                      varchar(255),
  price                     double,
  constraint pk_products primary key (id))
;

create table product_supplies (
  id                        bigint not null,
  name                      varchar(255),
  quantity                  integer,
  product_id                bigint,
  constraint pk_product_supplies primary key (id))
;

create table sales (
  id                        bigint not null,
  client                    varchar(255),
  sale_product_id           bigint,
  constraint pk_sales primary key (id))
;

create table sale_products (
  id                        bigint not null,
  name                      varchar(255),
  price                     double,
  sale_id                   bigint,
  constraint pk_sale_products primary key (id))
;

create table sale_product_supplies (
  id                        bigint not null,
  name                      varchar(255),
  quantity                  integer,
  sale_product_id           bigint,
  constraint pk_sale_product_supplies primary key (id))
;

create table supplies (
  id                        bigint not null,
  name                      varchar(255),
  quantity                  integer,
  constraint pk_supplies primary key (id))
;

create sequence products_seq;

create sequence product_supplies_seq;

create sequence sales_seq;

create sequence sale_products_seq;

create sequence sale_product_supplies_seq;

create sequence supplies_seq;

alter table product_supplies add constraint fk_product_supplies_product_1 foreign key (product_id) references products (id) on delete restrict on update restrict;
create index ix_product_supplies_product_1 on product_supplies (product_id);
alter table sales add constraint fk_sales_saleProduct_2 foreign key (sale_product_id) references sale_products (id) on delete restrict on update restrict;
create index ix_sales_saleProduct_2 on sales (sale_product_id);
alter table sale_products add constraint fk_sale_products_sale_3 foreign key (sale_id) references sales (id) on delete restrict on update restrict;
create index ix_sale_products_sale_3 on sale_products (sale_id);
alter table sale_product_supplies add constraint fk_sale_product_supplies_saleP_4 foreign key (sale_product_id) references sale_products (id) on delete restrict on update restrict;
create index ix_sale_product_supplies_saleP_4 on sale_product_supplies (sale_product_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists products;

drop table if exists product_supplies;

drop table if exists sales;

drop table if exists sale_products;

drop table if exists sale_product_supplies;

drop table if exists supplies;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists products_seq;

drop sequence if exists product_supplies_seq;

drop sequence if exists sales_seq;

drop sequence if exists sale_products_seq;

drop sequence if exists sale_product_supplies_seq;

drop sequence if exists supplies_seq;

