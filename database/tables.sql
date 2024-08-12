-- Có thể thêm các thông tin profile (tên, tuổi, giới tính,...) vào, hiện tại thì chưa cần thiết lắm
-- Password được hash bằng SHA256 với salt 16 bytes, encode hết về Base64 xong nối bằng dấu ':', nó sẽ thành dạng 'hash:salt'
-- Ở đây user ko có role, bởi vì web của mình chỉ có 1 role thôi, tài khoản và mật khẩu của admin (aka cái thằng tạo khóa học) sẽ lưu ở 1 file trên ổ cứng
create table [user] (
	[id] int primary key identity,
	[email] varchar(255) unique not null,
	[password_hash] char(69) not null,
	[created_at] datetime2 not null default getutcdate()
);

-- category làm thành bảng riêng để khi lấy ra filter ở homepage cho dễ
create table [category] (
	[id] int primary key identity,
	[name] nvarchar(255) unique not null
);

-- original_price là giá gốc, sale_price là giá bán, mình sẽ hiển thị sale_price cho người dùng, nếu ko có sale thì để sale_price = original_price
-- description hay tất cả các trường có kiểu ntext sẽ là html, khi lấy ra nhúng thẳng vào jsp
-- active là trạng thái hoạt động của course, khi mới tạo sẽ là inactive, khi nào nó là active thì người dùng mới có thể nhìn thấy và mua khóa học đó
-- trường hợp mình muốn ngừng bán course đấy thì mình chuyển nó về lại inactive, mình ko cho xóa bởi vì có thể có người dùng đã mua course
-- trường hợp mình muốn update nội dung của course thì cũng chuyển trạng thái về inactive, như thế đến khi mình active trở lại thì những người đã mua course đấy ko phải mua lại nữa
create table [course] (
	[id] int primary key identity,
	[title] nvarchar(255) not null,
	[description] ntext,
	[original_price] int not null,
	[sale_price] int not null,
	[image_path] nvarchar(max),
	[active] bit not null default(0),
	[created_at] datetime2 not null default getutcdate()
);

-- một course có thể gán nhiều thể loại
create table [course_category] (
	[course_id] int not null foreign key references [course]([id]) on delete cascade,
	[category_id] int not null foreign key references [category]([id]) on delete cascade,
	primary key([course_id], [category_id])
);

-- Có 2 loại content 'lesson' và 'quiz' cùng lưu vào trong một bảng này
-- blocked là để chỉ content đấy có cần phải mua mới vào được hay ko, blocked = 0 có nghĩa là người dùng ko cần mua course cx có thể vào đc (kiểu bài giảng miễn phí)
-- content sẽ có 2 trường hợp:
--     type == 'Lesson': content sẽ là html giống trường [description] của course
--     type == 'Quiz': content sẽ là xâu json, lưu các câu hỏi của quiz đấy. Vd:
--{
--    "pass_grade": 7, // số câu đúng để pass (<= tổng số câu)
--    "questions": [
--        {
--            "question_id": 1,
--            "question_text": "What does HTML stand for?",
--            "multiple_answers": false, // câu hỏi có một câu hay nhiều câu đúng
--            "options": [
--                {
--                    "option_id": 1,
--                    "option_text": "Hyper Text Markup Language",
--                    "is_correct": true
--                },
--                {
--                    "option_id": 2,
--                    "option_text": "Home Tool Markup Language",
--                    "is_correct": false
--                },
--                {
--                    "option_id": 3,
--                    "option_text": "Hyperlinks and Text Markup Language",
--                    "is_correct": false
--                },
--                {
--                    "option_id": 4,
--                    "option_text": "Hyperlinking Text Marking Language",
--                    "is_correct": false
--                }
--            ],
--            "explanation": "HTML stands for Hyper Text Markup Language, which is used to create web pages."
--        },
--	  ]
--}
-- Mình sẽ lưu thẳng cả cái đống trên vào content, xong khi cần thì lấy ra parse về một cái java class
create table [course_content] (
	[id] int primary key identity,
	[course_id] int not null foreign key references [course]([id]) on delete cascade,
	[type] varchar(255) not null check([type] in ('Lesson', 'Quiz')),
	[title] nvarchar(255) not null,
	[content] ntext not null,
	[blocked] bit not null,
	[created_at] datetime2 not null default getutcdate()
);

-- Bình thường course ko bị xóa mà chỉ để inactive thôi, nma nếu chẳng may bị xóa thì mình ko muốn cái payment liên quan cx bị xóa theo
-- Thế nên là để 'on delete set null', khi course bị xóa thì course_id sẽ thành null
-- 'on delete cascade' có nghĩa là khi mình xóa student (khi người dùng delete tài khoản) thì các cái payment của người dùng đấy cx bị xóa theo
-- course_title là để dự phòng, trong trường hợp course_id == null thì mình vx biết course mình mua là course gì
-- amount thì cứ để bằng sale_price của course
create table [payment] (
	[id] int primary key identity,
	[course_id] int foreign key references [course]([id]) on delete set null,
	[course_title] nvarchar(255) not null,
	[student_id] int not null foreign key references [user]([id]) on delete cascade,
	[amount] int not null,
	[paid_at] datetime2 not null default getutcdate()
);

-- Sau khi người dùng pay course xong thì tạo một cái enrollment
-- Khi người dùng đăng nhập vào thì mình lấy thông tin từ bảng này ra để hiển thị
-- Làm cái hàm kiểm tra nếu người dùng hoàn thành hết nội dung course thì update status thành Completed
create table [enrollment] (
	[id] int primary key identity,
	[course_id] int not null foreign key references [course]([id]) on delete cascade,
	[student_id] int not null foreign key references [user]([id]) on delete cascade,
	[status] varchar(255) not null check([status] in ('In Progress', 'Completed')) default('In Progress'),
	[enrolled_at] datetime2 not null default getutcdate()
);

-- Sau khi người dùng ấn 'Mark as completed' ở một lesson thì thêm một dòng ở đây
create table [lesson_progress] (
	[lesson_id] int not null foreign key references [course_content]([id]) on delete cascade,
	[student_id] int not null foreign key references [user]([id]) on delete cascade,
	primary key([lesson_id], [student_id])
);

-- grade = (số câu đúng / tổng số câu hỏi) * 10
create table [quiz_result] (
	[quiz_id] int not null foreign key references [course_content]([id]) on delete cascade,
	[student_id] int not null foreign key references [user]([id]) on delete cascade,
	[grade] decimal(19, 4) not null,
	primary key([quiz_id], [student_id])
);

create table [course_feedback] (
	[id] int primary key identity,
	[course_id] int not null foreign key references [course]([id]) on delete cascade,
	[student_id] int foreign key references [user]([id]) on delete set null,
	[content] nvarchar(max) not null,
	[created_at] datetime2 not null default getutcdate()
);

-- Thống kê cho từng khóa học, mình sẽ tạo 1 cái này mỗi khi mình tạo một course:
-- purchase_count: số người mua khóa học đấy
-- completed_count: số người đã hoàn thành khóa học đấy
-- profit: tổng doanh thu của khóa học đấy
-- Mình sẽ hiển thị những thông tin này trên dashboard
create table [course_report] (
	[id] int primary key identity,
	[course_id] int not null foreign key references [course]([id]) on delete cascade,
	[purchase_count] int not null,
	[completed_count] int not null,
	[profit] int not null
);