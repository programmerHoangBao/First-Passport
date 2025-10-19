-- Xóa policy cũ (nếu tồn tại) để tránh lỗi khi Flyway chạy lại
BEGIN
    DBMS_RLS.drop_policy(
        object_schema => 'GROUP5_USER',
        object_name   => 'PASSPORTS',
        policy_name   => 'PASSPORT_ROLE_POLICY'
    );
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -28102 THEN
            RAISE;
END IF;
END;
/

-- Tạo lại function policy cho bảng PASSPORT
CREATE OR REPLACE FUNCTION passport_policy (
    p_schema IN VARCHAR2,
    p_object IN VARCHAR2
) RETURN VARCHAR2
AUTHID CURRENT_USER
AS
    v_role VARCHAR2(50);
BEGIN
    -- Lấy role từ application context
    v_role := SYS_CONTEXT('passport_ctx', 'user_role');

    -- Nếu role là LT thì không được truy cập
    IF v_role = 'LT' THEN
        RETURN '1 = 0'; -- Không cho thấy dữ liệu
ELSE
        RETURN NULL; -- Truy cập bình thường
END IF;
END;
/

-- Gắn policy vào bảng PASSPORT
BEGIN
    DBMS_RLS.add_policy(
        object_schema   => 'GROUP5_USER',
        object_name     => 'PASSPORTS',
        policy_name     => 'PASSPORT_ROLE_POLICY',
        function_schema => 'GROUP5_USER',
        policy_function => 'passport_policy',
        statement_types => 'SELECT'
    );
END;
/
