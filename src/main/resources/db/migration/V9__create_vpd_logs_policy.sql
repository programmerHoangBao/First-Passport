-- V9__create_vpd_logs_policy.sql

-- Xóa policy cũ (nếu tồn tại) để tránh lỗi khi Flyway chạy lại
BEGIN
    DBMS_RLS.drop_policy(
        object_schema => 'GROUP5_USER',
        object_name   => 'LOGS',
        policy_name   => 'LOGS_ROLE_POLICY'
    );
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -28102 THEN
            RAISE;
END IF;
END;
/

-- Tạo lại function policy
CREATE OR REPLACE FUNCTION logs_policy (
    p_schema IN VARCHAR2,
    p_object IN VARCHAR2
) RETURN VARCHAR2
AUTHID CURRENT_USER
AS
    v_role VARCHAR2(50);
BEGIN
    v_role := SYS_CONTEXT('passport_ctx', 'user_role');

    -- [GS] (Giám sát) là vai trò duy nhất được xem logs
    IF v_role = 'GS' THEN
        RETURN NULL; -- Cho phép truy cập
ELSE
        -- Tất cả các vai trò khác (XT, XD, LT) không được xem logs
        RETURN '1 = 0';
END IF;
END;
/

-- Gắn policy này vào bảng LOGS
-- Quan trọng: Áp dụng cho cả SELECT (để xem) và DELETE (để ngăn xóa)
BEGIN
    DBMS_RLS.add_policy(
        object_schema   => 'GROUP5_USER',
        object_name     => 'LOGS',
        policy_name     => 'LOGS_ROLE_POLICY',
        function_schema => 'GROUP5_USER',
        policy_function => 'logs_policy',
        statement_types => 'SELECT, DELETE'
    );
END;