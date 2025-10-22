1. Tổng quan – Hệ thống Cấp Hộ Chiếu Lần Đầu
- Hệ thống Cấp Hộ Chiếu Lần Đầu được thiết kế nhằm hỗ trợ người dân đăng ký và theo dõi quá trình cấp hộ chiếu trực tuyến, đảm bảo tính chính xác, minh bạch và bảo mật dữ liệu cá nhân.
- Người sử dụng sẽ điền thông tin đăng ký (bao gồm: Họ và tên, địa chỉ thường trú, phái, CMND, điện thoại, email) thông qua form online, dữ liệu này được chuyển đến các bộ phận chức năng xử lý theo quy trình sau:

🔹 Quy trình xử lý
- Bộ phận Xác thực (XT): Tiếp nhận thông tin từ form đăng ký -> Đối chiếu dữ liệu người đăng ký với Resident Database (chứa thông tin công dân như CMND, hộ khẩu,...) -> Sau khi xác thực, gửi yêu cầu hợp lệ cho bộ phận Xét duyệt (XD).
- Bộ phận Xét duyệt (XD): Xem xét các thông tin trên form đăng ký -> Không có quyền truy cập dữ liệu trong Resident Database -> Phê duyệt hoặc từ chối yêu cầu cấp hộ chiếu.
- Bộ phận Lưu trữ (LT): Nhận kết quả phê duyệt từ bộ phận XD -> Lưu trữ trạng thái kết quả (đồng ý / không đồng ý cấp hộ chiếu) -> Không được truy cập thông tin cá nhân hoặc dữ liệu khác.
- Bộ phận Giám sát (GS): Theo dõi toàn bộ quy trình hoạt động của các bộ phận XT, XD, LT từ khi người dùng nộp đơn đến khi nhận kết quả. -> Đảm bảo tính minh bạch, kiểm soát sai phạm và truy vết khi cần thiết.

2. Cài đặt:
- Clone dự án về máy: -git clone https://github.com/programmerHoangBao/First-Passport.git
- Chạy file file script trong Oracle "First-Passport/src/main/scripts/Create_Group5_user.sql" với tài khoản SYSTEM trong service ORCLPDB.
- Tạo file .env trong thư mục First-Passport (mô tả từng thuộc tính trong .env)
  + SERVER_PORT (Cổng chạy ứng dụng):	Ứng dụng backend (thường là Spring Boot) sẽ khởi động trên cổng 7070. Khi chạy, bạn truy cập qua http://localhost:7070.
  + ORACLE_DB_URL	(Đường dẫn kết nối đến Oracle Database): Cấu trúc jdbc:oracle:thin:@//localhost:1521/ORCLPDB cho biết ứng dụng sẽ kết nối đến Oracle PDB (Pluggable Database) tên ORCLPDB đang chạy trên localhost qua port 1521.
  + ORACLE_DB_USERNAME (Tên người dùng cơ sở dữ liệu):	Tên user trong Oracle được cấp quyền truy cập (ở đây là GROUP5_USER) — thường tương ứng với schema chứa các bảng dữ liệu của ứng dụng.
  + ORACLE_DB_PASSWORD	(Mật khẩu cơ sở dữ liệu):	Mật khẩu để xác thực user GROUP5_USER khi kết nối tới Oracle Database.
  + SECRET_KEY	Khóa bí mật (JWT Secret Key)	Dùng để mã hóa và xác thực token JWT trong hệ thống đăng nhập. Đây là chuỗi ngẫu nhiên cần được giữ bí mật tuyệt đối, không chia sẻ công khai.
  + TOKEN_EXPIRATION_MS	Thời gian hết hạn của Access Token (JWT)	Đơn vị mili-giây (ms) — ở đây là 1800000 ms tương đương 30 phút. Sau thời gian này, người dùng phải lấy token mới để tiếp tục truy cập.
  + REFRESH_EXPIRATION_MS	(Thời gian hết hạn của Refresh Token)	Cũng tính bằng mili-giây — 604800000 ms tương đương 7 ngày. Dùng để lấy Access Token mới mà không cần đăng nhập lại.
  + HOST	(Máy chủ gửi email)	Máy chủ SMTP (ở đây là smtp.gmail.com) dùng để gửi email xác thực, thông báo, hoặc đặt lại mật khẩu.
  + PORT	(Cổng SMTP)	Cổng 587 được dùng cho kết nối SMTP qua TLS (bảo mật).
  + EMAIL	(Địa chỉ email của ứng dụng)	Địa chỉ Gmail dùng để gửi các email tự động đến người dùng (ví dụ: passport.service@gmail.com).
  + APP_PASSWORD	(Mật khẩu ứng dụng (App Password)):	Mật khẩu đặc biệt do Gmail cấp để các ứng dụng có thể gửi email qua SMTP. Không phải mật khẩu Gmail thật, được tạo trong Google Account → Security → App passwords.
- Chạy dự án.
3. Kết quả thực hiện:
Test từng API
  - API api/register
    + API này dùng để đăng kí cấp hộ chiếu lần đầu
    + Role thực hiện: người dùng muốn đăng kí cấp hộ chiếu lần đầu
<img width="1919" height="1123" alt="image" src="https://github.com/user-attachments/assets/ac028662-b3b7-4167-a1fd-a3887fb9b60e" />

  - API api/login
    + API này dành cho người dùng đã có tải khoản đăng nhập vào hệ thống
    + Role thực hiện: người dùng đã có tài khoản (XT, XD, LT, GS)
<img width="1919" height="1132" alt="image" src="https://github.com/user-attachments/assets/535e16f8-88f8-4bc1-ad04-657ab427dd44" />

  - API api/xt/view-all-registration
    + Request: là Params, Key = status, Value = PENDING, VERIFIED, REJECTED.
    + API này dùng để xem tất cả những yêu cầu đăng kí cấp hộ chiếu
    + Role thực hiện: XT

  - API xt/view-detail-registration
    + Request: là Params, Key là Long id
    + API này dùng để xem chi tiết thông tin yêu cầu đăng kí cấp hộ chiếu.
    + Role thực hiện: XT
   
  - API api/xt/send-form
    + Request: là Params, Key Long formId và Long userId
    + API này dùng để gửi yêu cầu đăng kí cấp hộ chiếu đến bộ phận XD
    + Role thực hiện: XT
   
  - API api/xt/reject-form
    + Request: là Params, Key Long formId
    + API này dùng để gửi từ chối yêu cầu đăng kí cấp hộ chiếu 
    + Role thực hiện: XT

  - API api/xt/view-detail-resident
    + Request: là Params, Key String identityNumber, message = "Identity Number must contain 12 digits"
    + API này dùng để xem chi tiết thông tin dân cư
    + Role thực hiện: XT

  - API api/xd/approval
    + Request: là Params, Key Long approvalId, Long approvalBy
    + API này dùng để chấp nhận yêu cầu đăng kí hộ chiếu từ bộ phận XT gửi đến
    + Role thực hiện: XD

  - API api/xd/all-approval-by-result
    + Request: là Params, Key int pageNumber và int pageSize
    + API này dùng để hiển thị danh sách approval theo kết quả
    + Role thực hiện: XD

  - API api/xd/all-send-from-to-xd
    + Request: là Params, Key int pageNumber và int pageSize
    + API này dùng để hiển thị tất cả cách form registration đã được xác thực
    + Role thực hiện: XD
   
  - API api/xd/view-detail-approval
    + Request: là Params, Key Long id
    + API này dùng để xem chi tiết thông tin approval
    + Role thực hiện: XD

  - API api/xd/reject-approval
    + Request: là Params, Key Long approvalId và Long approverBy
    + API này dùng để từ chối thông tin approval
    + Role thực hiện: XD
   
  - API api/lt/create-passport
    + Request: là Params, Key Long approvalId và Long userId
    + API này dùng để tạo passport
    + Role thực hiện: LT

  - API api/lt/all-request-store
    + Request: là Params, Key int pageNumber và int pageSize
    + API này dùng để lưu trữ tất cả các yêu cầu
    + Role thực hiện: LT
   
  - API api/lt/reject-passport
    + Request: là Params, Key Long approvalId và Long userId
    + API này dùng để từ chối các yêu cầu
    + Role thực hiện: LT

  - API  api/gs/view-all-log
    + Request: là Params, Key int pageNumber và int pageSize
    + API này dùng để xem tất cả các Log
    + Role thực hiện: 
