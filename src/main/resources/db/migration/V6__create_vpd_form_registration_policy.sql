-- V6__create_vpd_form_registration_policy.sql

-- Xóa policy cũ (nếu tồn tại) để tránh lỗi khi Flyway chạy lại
BEGIN
    DBMS_RLS.drop_policy(
        object_schema => 'GROUP5_USER',
        object_name   => 'FORM_REGISTRATION',
        policy_name   => 'FORM_REGISTRATION_ROLE_POLICY'
    );
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -28102 THEN
            RAISE;
        END IF;
END;
/

-- Tạo lại function policy
CREATE OR REPLACE FUNCTION form_registration_policy (
    p_schema IN VARCHAR2,
    p_object IN VARCHAR2
) RETURN VARCHAR2
AUTHID CURRENT_USER
AS
    v_role VARCHAR2(50);
BEGIN

    v_role := SYS_CONTEXT('passport_ctx', 'user_role');

    IF v_role IN ('XT', 'XD') THEN
        RETURN NULL; 
    ELSE
        RETURN '1 = 0';
    END IF;
END;
/

-- Gắn policy này vào bảng FORM_REGISTRATION
BEGIN
    DBMS_RLS.add_policy(
        object_schema   => 'GROUP5_USER',
        object_name     => 'FORM_REGISTRATION',
        policy_name     => 'FORM_REGISTRATION_ROLE_POLICY',
        function_schema => 'GROUP5_USER',
        policy_function => 'form_registration_policy',
        statement_types => 'SELECT'
    );
END;
/