<?php
 
// 설정
$uploads_dir = './uploads';
$allowed_ext = array('jpg','jpeg','png','gif');
 
// 변수 정리
$error = $_FILES['myfile']['error'];
$name = $_FILES['myfile']['name'];
$ext = array_pop(explode('.', $name));

$error2 = $_FILES['myfile2']['error'];
$name2 = $_FILES['myfile2']['name'];
$ext2 = array_pop(explode('.', $name2));
 
// 오류 확인
if( $error != UPLOAD_ERR_OK ) {
	switch( $error ) {
		case UPLOAD_ERR_INI_SIZE:
		case UPLOAD_ERR_FORM_SIZE:
			echo "파일이 너무 큽니다. ($error)";
			break;
		case UPLOAD_ERR_NO_FILE:
			echo "파일이 첨부되지 않았습니다. ($error)";
			break;
		default:
			echo "파일이 제대로 업로드되지 않았습니다. ($error)";
	}
	exit;
}

// 오류 확인
if( $error2 != UPLOAD_ERR_OK ) {
	switch( $error2 ) {
		case UPLOAD_ERR_INI_SIZE:
		case UPLOAD_ERR_FORM_SIZE:
			echo "파일이 너무 큽니다. ($error2)";
			break;
		case UPLOAD_ERR_NO_FILE:
			echo "파일이 첨부되지 않았습니다. ($error2)";
			break;
		default:
			echo "파일이 제대로 업로드되지 않았습니다. ($error2)";
	}
	exit;
}
 
// 확장자 확인
if( !in_array($ext, $allowed_ext) ) {
	echo "허용되지 않는 확장자입니다.";
	exit;
}

// 확장자 확인
if( !in_array($ext2, $allowed_ext) ) {
	echo "허용되지 않는 확장자입니다.";
	exit;
}
 
// 파일 이동
move_uploaded_file( $_FILES['myfile']['tmp_name'], "$uploads_dir/$name");

move_uploaded_file( $_FILES['myfile2']['tmp_name'], "$uploads_dir/$name2");

// 파일 정보 출력
echo "<h2>파일 정보</h2>
	<ul>
		<li>파일명: $name</li>
		<li>확장자: $ext</li>
		<li>파일형식: {$_FILES['myfile']['type']}</li>
		<li>파일크기: {$_FILES['myfile']['size']} 바이트</li>
	</ul>";

echo "<h2>파일 정보</h2>
	<ul>
		<li>파일명: $name2</li>
		<li>확장자: $ext2</li>
		<li>파일형식: {$_FILES['myfile2']['type']}</li>
		<li>파일크기: {$_FILES['myfile2']['size']} 바이트</li>
	</ul>";


?>