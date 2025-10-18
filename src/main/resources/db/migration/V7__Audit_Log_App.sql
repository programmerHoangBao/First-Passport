-- #!/usr/bin/env flyway:oracle
-- #set delimiter /

CREATE OR REPLACE PROCEDURE log_fga_event_handler (
  p_object_schema VARCHAR2,
  p_object_name   VARCHAR2,
  p_policy_name   VARCHAR2
) AS
  v_user_name   VARCHAR2(100);
  v_user_role   VARCHAR2(20);
  v_action      VARCHAR2(50);
  v_sql_text    CLOB;
  v_sql_id      VARCHAR2(13);
BEGIN
  v_user_name := SYS_CONTEXT('passport_ctx', 'user_name');
  v_user_role := SYS_CONTEXT('passport_ctx', 'user_role');

  v_action := CASE
                WHEN p_policy_name LIKE '%SELECT%' THEN 'SELECT'
                WHEN p_policy_name LIKE '%INSERT%' THEN 'INSERT'
                WHEN p_policy_name LIKE '%UPDATE%' THEN 'UPDATE'
                WHEN p_policy_name LIKE '%DELETE%' THEN 'DELETE'
                ELSE 'UNKNOWN'
              END;

  INSERT INTO LOGS (
    ACTOR, ROLE, ACTION, EVENT_TIME, POLICY_NAME, OBJECT_NAME
  ) VALUES (
    v_user_name,
    v_user_role,
    v_action,
    SYSTIMESTAMP,
    p_policy_name,
    p_object_name
  );

EXCEPTION
  WHEN OTHERS THEN
    NULL;
END;
/

-- Xóa các policy cũ (nếu có)
BEGIN
  DBMS_FGA.drop_policy('GROUP5_USER', 'RESIDENT_DATA', 'FGA_SELECT_RESIDENT_DATA');
  DBMS_FGA.drop_policy('GROUP5_USER', 'FORM_REGISTRATION', 'FGA_SELECT_FORM_REGISTRATION');
  DBMS_FGA.drop_policy('GROUP5_USER', 'FORM_REGISTRATION', 'FGA_INSERT_FORM_REGISTRATION');
  DBMS_FGA.drop_policy('GROUP5_USER', 'FORM_REGISTRATION', 'FGA_UPDATE_FORM_REGISTRATION');
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_SELECT_APPROVAL');
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_INSERT_APPROVAL'); 
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_UPDATE_APPROVAL');
  DBMS_FGA.drop_policy('GROUP5_USER', 'APPROVAL_DATA', 'FGA_DELETE_APPROVAL');
  DBMS_FGA.drop_policy('GROUP5_USER', 'PASSPORTS', 'FGA_INSERT_PASSPORT');
EXCEPTION
  WHEN OTHERS THEN NULL;
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'RESIDENT_DATA',
    policy_name     => 'FGA_SELECT_RESIDENT_DATA',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'FORM_REGISTRATION',
    policy_name     => 'FGA_SELECT_FORM_REGISTRATION',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'FORM_REGISTRATION',
    policy_name     => 'FGA_INSERT_FORM_REGISTRATION',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'INSERT',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'FORM_REGISTRATION',
    policy_name     => 'FGA_UPDATE_FORM_REGISTRATION',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'UPDATE',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_SELECT_APPROVAL',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'SELECT',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_INSERT_APPROVAL',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'INSERT',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_UPDATE_APPROVAL',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'UPDATE',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'APPROVAL_DATA',
    policy_name     => 'FGA_DELETE_APPROVAL',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'DELETE',
    enable          => TRUE
  );
END;
/

BEGIN
  DBMS_FGA.add_policy(
    object_schema   => 'GROUP5_USER',
    object_name     => 'PASSPORTS',
    policy_name     => 'FGA_INSERT_PASSPORT',
    handler_module  => 'LOG_FGA_EVENT_HANDLER',
    statement_types => 'INSERT',
    enable          => TRUE
  );
END;
/
