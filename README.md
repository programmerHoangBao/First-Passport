# PROJECT: FIRST-PASSPORT BACK-END 

Database: ORACLE

**1. Overview – First-Time Passport Issuance System**
- The First-Time Passport Issuance System is designed to assist citizens in registering and tracking the passport issuance process online, ensuring accuracy, transparency, and the security of personal data.
- Users fill in registration information (including full name, permanent address, gender, ID number, phone number, and email) through an online form. This data is then transferred to the respective functional departments for processing according to the following workflow:

🔹 **Processing Workflow**
- **Verification Department (XT):** Receives information from the registration form → Cross-checks applicant data with the **Resident Database** (which contains citizen information such as ID number, household registration, etc.) → After successful verification, forwards valid requests to the **Approval Department (XD)** for further processing.

- **Approval Department (XD):** Reviews the information provided in the registration form → Does **not** have access to data in the **Resident Database** → Approves or rejects the passport issuance request.
- **Archiving Department (LT):** Receives the approval results from the **Approval Department (XD)** → Stores the status of the outcome (approved/rejected for passport issuance) → Does **not** have access to personal information or any other data.

- **Supervision Department (GS):** Monitors the entire operational process of the **Verification (XT)**, **Approval (XD)**, and **Archiving (LT)** departments from the time the user submits the application until the final result is issued → Ensures transparency, detects and controls potential violations, and enables traceability when necessary.


**2. Installation:**

* Clone the project to your local machine:

  ```bash
  git clone https://github.com/programmerHoangBao/First-Passport.git
  ```

* Run the SQL script file in Oracle:
  `First-Passport/src/main/scripts/Create_Group5_user.sql` using the **SYSTEM** account within the **ORCLPDB** service.

* Create a `.env` file in the **First-Passport** directory (description of each property below):

  * **SERVER_PORT** *(Application Port)*:
    The backend application (typically a Spring Boot app) will run on port **7070**.
    Access it via: [http://localhost:7070](http://localhost:7070).

  * **ORACLE_DB_URL** *(Oracle Database Connection URL)*:
    Format `jdbc:oracle:thin:@//localhost:1521/ORCLPDB` indicates the app connects to an Oracle **Pluggable Database (PDB)** named **ORCLPDB** running on **localhost** through port **1521**.

  * **ORACLE_DB_USERNAME** *(Database Username)*:
    The Oracle user with granted access (in this case **GROUP5_USER**) — usually corresponding to the schema that holds the application's data tables.

  * **ORACLE_DB_PASSWORD** *(Database Password)*:
    The password used to authenticate **GROUP5_USER** when connecting to the Oracle Database.

  * **SECRET_KEY** *(JWT Secret Key)*:
    A secret string used to sign and verify JWT tokens in the authentication system.
    It must be kept **strictly confidential** and never shared publicly.

  * **TOKEN_EXPIRATION_MS** *(Access Token Expiration Time)*:
    Expressed in milliseconds — **1800000 ms** equals **30 minutes**.
    After this period, users must request a new access token to continue.

  * **REFRESH_EXPIRATION_MS** *(Refresh Token Expiration Time)*:
    Also in milliseconds — **604800000 ms** equals **7 days**.
    Used to obtain a new access token without re-logging in.

  * **HOST** *(Email Host)*:
    SMTP server (e.g., **smtp.gmail.com**) used for sending verification, notification, or password reset emails.

  * **PORT** *(SMTP Port)*:
    Port **587** is used for SMTP connections over **TLS** (secure transmission).

  * **EMAIL** *(Application Email Address)*:
    The Gmail address used by the system to send automated emails to users
    (e.g., **[passport.service@gmail.com](mailto:passport.service@gmail.com)**).

  * **APP_PASSWORD** *(App Password)*:
    A special password issued by Gmail for applications to send emails via SMTP.
    It is **not** your regular Gmail password and can be created via
    *Google Account → Security → App passwords*.

* Finally, **run the project**.

**3. Implementation Results:**
**API Testing**

* **API:** `api/register`

  * **Purpose:** Used for first-time passport registration.
  * **Role:** Executed by users who want to apply for their first passport.

<img width="1919" height="1123" alt="image" src="https://github.com/user-attachments/assets/ac028662-b3b7-4167-a1fd-a3887fb9b60e" />

 * **API:** `api/login`

  * **Purpose:** Allows users with existing accounts to log in to the system.
  * **Role:** Executed by users who already have an account (**XT**, **XD**, **LT**, **GS**).

<img width="1919" height="1132" alt="image" src="https://github.com/user-attachments/assets/535e16f8-88f8-4bc1-ad04-657ab427dd44" />

* **API:** `api/xt/view-all-registration`

  * **Request:** Uses parameters — **Key = status**, **Value = PENDING**, **VERIFIED**, or **REJECTED**.
  * **Purpose:** Retrieves all passport registration requests.
  * **Role:** Executed by the **Verification Department (XT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/b14efea8-edcd-4a2c-94de-87c51e967a06" />

* **API:** `api/xt/view-detail-registration`

  * **Request:** Uses parameters — **Key = id** (of type *Long*).
  * **Purpose:** Displays detailed information about a specific passport registration request.
  * **Role:** Executed by the **Verification Department (XT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/1a324a22-1b29-4e41-8b5c-c538203631f7" />

* **API:** `api/xt/send-form`

  * **Request:** Uses parameters — **Keys:** *formId* (Long) and *userId* (Long).
  * **Purpose:** Sends the verified passport registration request to the **Approval Department (XD)**.
  * **Role:** Executed by the **Verification Department (XT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/a2d29741-c839-49f8-8d7c-2a9f80e0b9f7" />

* **API:** `api/xt/reject-form`

  * **Request:** Uses parameters — **Key:** *formId* (Long).
  * **Purpose:** Sends a rejection response for a passport registration request.
  * **Role:** Executed by the **Verification Department (XT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/24ffad04-4721-47ee-9178-188b9a46daf7" />

* **API:** `api/xt/view-detail-resident`

  * **Request:** Uses parameters — **Key:** *identityNumber* (String).

    * Validation message: `"Identity Number must contain 12 digits"`.
  * **Purpose:** Retrieves detailed information about a resident from the Resident Database.
  * **Role:** Executed by the **Verification Department (XT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/5d99322b-e833-4788-bc2c-da567c299c2d" />


* **API:** `api/xd/approval`

  * **Request:** Uses parameters — **Keys:** *approvalId* (Long) and *approvalBy* (Long).
  * **Purpose:** Approves the passport registration request sent from the **Verification Department (XT)**.
  * **Role:** Executed by the **Approval Department (XD)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/162b5d53-eb18-4cea-b909-52fd37cee630" />

* **API:** `api/xd/all-approval-by-result`

  * **Request:** Uses parameters — **Keys:** *pageNumber* (int) and *pageSize* (int).
  * **Purpose:** Displays a paginated list of approval records filtered by result status.
  * **Role:** Executed by the **Approval Department (XD)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/3c0ec404-e8f8-4a34-8278-d2df715516f0" />

* **API:** `api/xd/all-send-from-to-xd`

  * **Request:** Uses parameters — **Keys:** *pageNumber* (int) and *pageSize* (int).
  * **Purpose:** Displays all registration forms that have been verified and sent to the **Approval Department (XD)**.
  * **Role:** Executed by the **Approval Department (XD)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/806b7a60-fb12-46c6-9864-bf89d10f079f" />

* **API:** `api/xd/view-detail-approval`

  * **Request:** Uses parameters — **Key:** *id* (Long).
  * **Purpose:** Retrieves detailed information about a specific approval record.
  * **Role:** Executed by the **Approval Department (XD)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/93237a67-50ff-4ea9-a5a4-9785763300e2" />

 * **API:** `api/xd/reject-approval`

  * **Request:** Uses parameters — **Keys:** *approvalId* (Long) and *approverBy* (Long).
  * **Purpose:** Rejects a specific approval request.
  * **Role:** Executed by the **Approval Department (XD)**.

  <img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/341411c3-3fbf-4091-8584-0f3c2031a152" />

* **API:** `api/lt/create-passport`

  * **Request:** Uses parameters — **Keys:** *approvalId* (Long) and *userId* (Long).
  * **Purpose:** Creates a new passport record based on the approved request.
  * **Role:** Executed by the **Archiving Department (LT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/ed856717-48fe-4831-97fe-abbf40273d30" />

* **API:** `api/lt/all-request-store`

  * **Request:** Uses parameters — **Keys:** *pageNumber* (int) and *pageSize* (int).
  * **Purpose:** Stores and displays all processed passport issuance requests.
  * **Role:** Executed by the **Archiving Department (LT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/d7d77437-e92e-4552-86c9-45bae12e6026" />

* **API:** `api/lt/reject-passport`

  * **Request:** Uses parameters — **Keys:** *approvalId* (Long) and *userId* (Long).
  * **Purpose:** Rejects passport issuance requests.
  * **Role:** Executed by the **Archiving Department (LT)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/e932492d-52ce-4597-baa3-b5732ac5da82" />

* **API:** `api/gs/view-all-log`

  * **Request:** Uses parameters — **Keys:** *pageNumber* (int) and *pageSize* (int).
  * **Purpose:** Displays all system logs for monitoring and auditing purposes.
  * **Role:** Executed by the **Supervision Department (GS)**.

<img width="2560" height="1528" alt="image" src="https://github.com/user-attachments/assets/c4c2810a-6475-49a5-b212-5efb7f18af5b" />

