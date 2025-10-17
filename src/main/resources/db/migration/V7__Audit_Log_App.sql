-- #!/usr/bin/env flyway:oracle
-- #set delimiter /

CREATE OR REPLACE PROCEDURE log_fga_event_handler (
  p_object_schema VARCHAR2,
  p_object_name   VARCHAR2,
  p_policy_name   VARCHAR2
) AS
  v_user_name   VARCHAR2(100);
  v_user_role   VARCHAR2(20);
  v_action      VARCHAR2(20);
  v_sql_text    CLOB;
  v_actor_id    NUMBER;
  v_sql_id      VARCHAR2(13);
BEGIN
  -- Lấy thông tin user
  v_user_name := SYS_CONTEXT('passport_ctx', 'user_name');
  v_user_role := SYS_CONTEXT('passport_ctx', 'user_role');

  -- Lấy ID user từ bảng USERS
BEGIN
SELECT ID INTO v_actor_id
FROM USERS
WHERE USERNAME = v_user_name;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_actor_id := NULL;
END;
BEGIN
    v_sql_id := SYS_CONTEXT('USERENV', 'CURRENT_SQL_ID');
EXCEPTION
    WHEN OTHERS THEN
      v_sql_id := NULL;
END;

  -- Lấy SQL_TEXT an toàn
  IF v_sql_id IS NOT NULL THEN
BEGIN
SELECT SQL_FULLTEXT INTO v_sql_text
FROM V$SQL
WHERE SQL_ID = v_sql_id AND ROWNUM = 1;
EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_sql_text := 'UNKNOWN SQL';
WHEN OTHERS THEN
        v_sql_text := 'ERROR FETCHING SQL TEXT';
END;
ELSE
    v_sql_text := 'NO SQL_ID AVAILABLE';
END IF;

  v_action := NVL(SYS_CONTEXT('USERENV', 'ACTION'), 'UNKNOWN');

  -- Ghi log
INSERT INTO LOGS (
    ACTOR, ROLE, ACTION, EVENT_TIME, POLICY_NAME, OBJECT_NAME, SQL_TEXT
) VALUES (
             v_actor_id,
             v_user_role,
             v_action,
             SYSTIMESTAMP,
             p_policy_name,
             p_object_name,
             v_sql_text
         );
EXCEPTION
  WHEN OTHERS THEN
    NULL;
END;
/

-- Xóa các policy cũ (nếu có)
BEGIN
  DBMS_FGA.drop_policy('GROUP5_USER', 'RESIDENT_DATA', 'FGA_RESIDENT_DATA_XT');
  DBMS_FGA.drop_policy('GROUP5_USER', 'FORM_REGISTRATION', 'FGA_FORM_REG_XT_XD');
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_APPROVAL_XT');
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_APPROVAL_XD');
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_APPROVAL_LT');
  DBMS_FGA.drop_policy('GROUP5_USER', 'PASSPORTS', 'FGA_PASSPORT_LT');
EXCEPTION
  WHEN OTHERS THEN NULL;
END;
/

-- RESIDENT_DATA: XT
BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'RESIDENT_DATA',
    policy_name     => 'FGA_RESIDENT_DATA_XT',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT',
    enable          => TRUE
  );
END;
/

-- FORM_REGISTRATION: XT, XD
BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'FORM_REGISTRATION',
    policy_name     => 'FGA_FORM_REG_XT_XD',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT,UPDATE',
    enable          => TRUE
  );
END;
/

-- APPROVAL_DATA: XT
BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_APPROVAL_XT',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'INSERT',
    enable          => TRUE
  );
END;
/

-- APPROVAL_DATA: XD
BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_APPROVAL_XD',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT,INSERT,UPDATE,DELETE',
    enable          => TRUE
  );
END;
/

-- APPROVAL_DATA: LT
BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_APPROVAL_LT',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT,DELETE',
    enable          => TRUE
  );
END;
/

-- PASSPORTS: LT
BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'PASSPORTS',
    policy_name     => 'FGA_PASSPORT_LT',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'INSERT',
    enable          => TRUE
  );
END;
/
