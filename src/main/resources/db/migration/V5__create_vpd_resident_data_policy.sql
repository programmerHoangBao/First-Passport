-- V5__create_vpd_resident_data_policy.sql

-- Xóa policy cũ (nếu tồn tại) để tránh lỗi khi chạy lại Flyway
BEGIN
    DBMS_RLS.drop_policy(
        object_schema => 'GROUP5_USER',
        object_name   => 'RESIDENT_DATA',
        policy_name   => 'RESIDENT_DATA_ROLE_POLICY'
    );
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -28102 THEN  -- policy không tồn tại
            RAISE;
        END IF;
END;
/

-- Tạo lại function policy
CREATE OR REPLACE FUNCTION resident_data_policy (
    p_schema IN VARCHAR2,
    p_object IN VARCHAR2
) RETURN VARCHAR2
AUTHID CURRENT_USER
AS
    v_role VARCHAR2(50);
BEGIN
    v_role := SYS_CONTEXT('passport_ctx', 'user_role');

    IF v_role = 'XT' THEN
        -- Cho phép truy cập tất cả
        RETURN NULL;
    ELSE
        -- Chặn truy cập
        RETURN '1 = 0';
    END IF;
END;
/

-- Thêm policy gắn vào bảng RESIDENT_DATA
BEGIN
    DBMS_RLS.add_policy(
        object_schema   => 'GROUP5_USER',
        object_name     => 'RESIDENT_DATA',
        policy_name     => 'RESIDENT_DATA_ROLE_POLICY',
        function_schema => 'GROUP5_USER',
        policy_function => 'resident_data_policy',
        statement_types => 'SELECT'
    );
END;
/
