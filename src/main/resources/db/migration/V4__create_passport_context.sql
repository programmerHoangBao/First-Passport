CREATE OR REPLACE PACKAGE set_passport_ctx_pkg AS
  PROCEDURE set_user_info(p_username VARCHAR2, p_role VARCHAR2);
  PROCEDURE clear_user_info;
END set_passport_ctx_pkg;
/

CREATE OR REPLACE PACKAGE BODY set_passport_ctx_pkg AS
  PROCEDURE set_user_info(p_username VARCHAR2, p_role VARCHAR2) IS
  BEGIN
    DBMS_SESSION.SET_CONTEXT('passport_ctx', 'user_name', p_username);
    DBMS_SESSION.SET_CONTEXT('passport_ctx', 'user_role', p_role);
  END;

  PROCEDURE clear_user_info IS
  BEGIN
    DBMS_SESSION.CLEAR_CONTEXT('passport_ctx');
  END;
END set_passport_ctx_pkg;
/
