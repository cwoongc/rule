set SEARCH_PATH = 'rule_test';
set SCHEMA 'rule_test';


CREATE TABLE rule (
  rule_id BIGSERIAL PRIMARY KEY
  , rule_type_id VARCHAR NOT NULL
  , rule_target_id VARCHAR
);

COMMENT ON TABLE rule is '룰';
COMMENT ON COLUMN rule.rule_id is '룰 ID';
COMMENT ON COLUMN rule.rule_type_id is '룰 유형 ID';
COMMENT ON COLUMN rule.rule_target_id is '룰 대상 ID';

CREATE TABLE entity_condition (
  entity_condition_id BIGSERIAL PRIMARY KEY
  , condition_entity_type_id VARCHAR NOT NULL
  , condition_entity_instance_id VARCHAR NOT NULL
  , rule_id BIGINT NOT NULL
);

COMMENT ON TABLE entity_condition is '엔티티 조건';
COMMENT ON COLUMN entity_condition.entity_condition_id is '엔티티 조건 ID';
COMMENT ON COLUMN entity_condition.condition_entity_type_id is '조건 엔티티 유형 ID';
COMMENT ON COLUMN entity_condition.condition_entity_instance_id is '조건 엔티티 인스턴스 ID';
COMMENT ON COLUMN entity_condition.rule_id is '룰 ID';

CREATE TABLE derived_condition (
  derived_condition_id BIGSERIAL PRIMARY KEY
  ,derived_condition_type_id VARCHAR NOT NULL
  ,rule_id BIGINT NOT NULL
);

COMMENT ON TABLE derived_condition is '파생 조건';
COMMENT ON COLUMN derived_condition.derived_condition_id is '파생 조건 ID';
COMMENT ON COLUMN derived_condition.derived_condition_type_id is '파생 조건 유형 ID';
COMMENT ON COLUMN derived_condition.rule_id is '룰 ID';


CREATE TABLE derived_condition_value_type (
   derived_condition_value_type_id VARCHAR PRIMARY KEY
  ,derived_condition_value_name VARCHAR NOT NULL
  ,derived_condition_value_data_type VARCHAR NOT NULL
);


COMMENT ON TABLE derived_condition_value_type is '파생 조건 값 유형';
COMMENT ON COLUMN derived_condition_value_type.derived_condition_value_type_id is '파생 조건 값 유형 ID';
COMMENT ON COLUMN derived_condition_value_type.derived_condition_value_name is '파생 조건 값 이름 ID';
COMMENT ON COLUMN derived_condition_value_type.derived_condition_value_data_type is '파생 조건 값 데이타 타입';




CREATE TABLE derived_condition_value (
   derived_condition_id BIGINT NOT NULL
  ,derived_condition_value_type_id VARCHAR NOT NULL
  ,derived_condition_value VARCHAR NOT NULL
);


COMMENT ON TABLE derived_condition_value is '파생 조건 값';
COMMENT ON COLUMN derived_condition_value.derived_condition_id is '파생 조건 ID';
COMMENT ON COLUMN derived_condition_value.derived_condition_value_type_id is '파생 조건 값 유형 ID';
COMMENT ON COLUMN derived_condition_value.derived_condition_value is '파생 조건 값';


ALTER TABLE derived_condition_value
ADD PRIMARY KEY (derived_condition_id, derived_condition_value_type_id);


CREATE TABLE rule_test.coupon (
  coupon_id BIGSERIAL PRIMARY KEY
  ,coupon_name VARCHAR NOT NULL
);


insert into rule_test.coupon (coupon_name) VALUES ('여름맞이 피자할인');


select * from coupon;

insert into rule (rule_type_id, rule_target_id) values ('coupon_issue_rule', 1);

insert into rule (rule_type_id, rule_target_id) values ('coupon_apply_rule', 1);

insert into rule (rule_type_id, rule_target_id) values ('coupon_apply_rule', 1);

select * from rule;


select * from derived_condition ;


insert into derived_condition (derived_condition_type_id, rule_id) VALUES ('so_order_hist_exist', 1);


insert into derived_condition_value (derived_condition_id, derived_condition_value_type_id, derived_condition_value) VALUES (1, 'so_order_hist_exist_tf', 'false');


insert into derived_condition_value_type (derived_condition_value_type_id, derived_condition_value_name, derived_condition_value_data_type)
VALUES ('so_order_hist_exist_tf', '사용자 시럽오더 주문 이력 존재 여부', 'BOOLEAN');





insert into entity_condition (condition_entity_type_id, condition_entity_instance_id, rule_id)
select
  'brand__ord_item_brand'
  , (select brand_id from public.brand b where b.brand_src_type_id = '02' and b.brand_name like '피자헛')
  , 2
;

insert into entity_condition (condition_entity_type_id, condition_entity_instance_id, rule_id)
  select
    'brand__ord_item_brand'
    , (select brand_id from public.brand b where b.brand_src_type_id = '02' and b.brand_name like '미스터피자')
    , 3
;


select * from entity_condition;






-- 쿠폰을 발급이 가능한지를 판단~?
-- 쿠폰이 무언지는 정해져잇음 (쿠폰 ID는 파라메터)


-- 쿠폰(룰 대상)ID을 이용하여
-- "쿠폰이 발급되기 위한 조건" 을 검색한다.

select
  r.rule_id
  ,'entity' as condition_kind
  , ec.condition_entity_type_id as condition_type_id
  , ec.condition_entity_instance_id
  , null as derived_condition_value_type_id
  , null as derived_condition_value
  , null as derived_condition_value_name
  , null as derived_condition_value_data_type
from rule r join entity_condition ec on r.rule_id = ec.rule_id
where 1=1
  and r.rule_type_id = 'coupon_issue_rule'
  and r.rule_target_id = '1'
UNION ALL
select
  r.rule_id
  ,'derived' as condition_kind
  , dc.derived_condition_type_id as condition_type_id
  , NULL
  , dcv.derived_condition_value_type_id
  , dcv.derived_condition_value
  , dcvt.derived_condition_value_name
  , dcvt.derived_condition_value_data_type
from rule r join derived_condition dc
    on r.rule_id = dc.rule_id
    join derived_condition_value dcv on dc.derived_condition_id = dcv.derived_condition_id
    left join derived_condition_value_type dcvt on dcv.derived_condition_value_type_id = dcvt.derived_condition_value_type_id
WHERE 1=1
      and r.rule_type_id = 'coupon_issue_rule' -- 룰이 쿠폰 발급 룰인가
      and r.rule_target_id = '1'
;





-- 쿠폰(룰 대상)ID을 이용하여
-- "해당 쿠폰을 적용하기 위해 확인해야 될 룰(조건 set)" 을 검색한다.
select
  r.rule_id
  ,r.rule_type_id
  , 'entity' as condition_kind
  , ec.condition_entity_type_id as condition_type_id
  , ec.condition_entity_instance_id
  , null as derived_condition_value_type_id
  , null as derived_condition_value
  , null as derived_condition_value_name
  , null as derived_condition_value_data_type
from rule r join entity_condition ec on r.rule_id = ec.rule_id
where 1=1
      and r.rule_type_id = 'coupon_apply_rule'
      and r.rule_target_id = '1'
UNION ALL
select
  r.rule_id
  ,r.rule_type_id
  ,'derived' as condition_kind
  , dc.derived_condition_type_id as condition_type_id
  , NULL
  , dcv.derived_condition_value_type_id
  , dcv.derived_condition_value
  , dcvt.derived_condition_value_name
  , dcvt.derived_condition_value_data_type
from rule r join derived_condition dc
    on r.rule_id = dc.rule_id
  join derived_condition_value dcv on dc.derived_condition_id = dcv.derived_condition_id
  left join derived_condition_value_type dcvt on dcv.derived_condition_value_type_id = dcvt.derived_condition_value_type_id
WHERE 1=1
      and r.rule_type_id = 'coupon_apply_rule' -- 룰이 쿠폰 발급 룰인가
      and r.rule_target_id = '1'
;

select 1;


















-- 쿠폰(룰 대상)ID을 이용하여
-- "해당 쿠폰을 적용하기 위해 확인해야 될 룰(조건 set)" 을 검색한다.
select
  r.rule_id
  ,r.rule_type_id
  ,r.rule_target_id
  , 'entity' as condition_kind
  , ec.condition_entity_type_id as condition_type_id
  , ec.condition_entity_instance_id
  , null as derived_condition_value_type_id
  , null as derived_condition_value
  , null as derived_condition_value_name
  , null as derived_condition_value_data_type
from rule r join entity_condition ec on r.rule_id = ec.rule_id
where 1=1
      and r.rule_type_id = 'coupon_apply_rule'
      and r.rule_target_id = '1'
UNION ALL
select
  r.rule_id
  ,r.rule_type_id
  ,r.rule_target_id
  ,'derived' as condition_kind
  , dc.derived_condition_type_id as condition_type_id
  , NULL
  , dcv.derived_condition_value_type_id
  , dcv.derived_condition_value
  , dcvt.derived_condition_value_name
  , dcvt.derived_condition_value_data_type
from rule r join derived_condition dc
    on r.rule_id = dc.rule_id
  join derived_condition_value dcv on dc.derived_condition_id = dcv.derived_condition_id
  left join derived_condition_value_type dcvt on dcv.derived_condition_value_type_id = dcvt.derived_condition_value_type_id
WHERE 1=1
      and r.rule_type_id = 'coupon_apply_rule' -- 룰이 쿠폰 발급 룰인가
      and r.rule_target_id = '1'
;

