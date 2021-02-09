<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!-- sorting -->
<div class="recordSort text-center">
  <button type="button" class="ing btn btn-lg btn-primary selected">문의하기</button>
  <button type="button" class="ing btn btn-lg btn-info" onclick="location.href='${pageContext.request.contextPath}/user/inquiry_list.cider'">문의내역</button>
</div>

<form class="form-horizontal" id="inquiry-form" enctype=“multipart/form-data” role="form">
  <fieldset>
    <legend class="sr-only">문의하기</legend>
    <div class="form-group">
      <label for="user_name" class="col-sm-2 control-label">이름 <span class="star">*</span></label>
      <div class="col-sm-10">
        <input type="text" name="user_name" id="user_name" class="form-control" maxlength="20" placeholder="이름을 입력해 주세요.">
      </div>
    </div>
    <div class="form-group">
      <label for="tel" class="col-sm-2 control-label">연락처 <span class="star">*</span></label>
      <div class="col-sm-10">
        <input type="tel" name="tel" id="tel" class="form-control" placeholder="숫자만 입력해 주세요.">
      </div>
    </div>
    <div class="form-group">
      <label for="email" class="col-sm-2 control-label">이메일 <span class="star">*</span></label>
      <div class="col-sm-10">
        <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력해 주세요.">
      </div>
    </div>
    <div class="form-group">
      <label for="sort" class="col-sm-2 control-label">유형 <span class="star">*</span></label>
      <div class="col-sm-10">
        <select class="form-control" id="sort" name="sort">
          <option value="">--- 선택하세요 ---</option>
          <option value="member">회원관련</option>
          <option value="product">상품관련</option>
          <option value="order">주문관련</option>
          <option value="shipment">배송관련</option>
          <option value="etc">기타</option>
        </select>
      </div>
    </div>
    <div class="form-group">
      <label for="title" class="col-sm-2 control-label">제목 <span class="star">*</span></label>
      <div class="col-sm-10">
        <input type="text" name="title" id="title" class="form-control" placeholder="제목을 입력해 주세요.">
      </div>
    </div>
    <div class="form-group">
      <label for="content" class="col-sm-2 control-label">내용 <span class="star">*</span></label>
      <div class="col-sm-10">
        <textarea name="content" id="content" maxlength="200" class="form-control" placeholder="내용을 입력해 주세요."></textarea>
        <span class="help-block">* 200자 이내</span>
      </div>
    </div>
    <div class="form-group">
      <label for="imgUpload" class="col-sm-2 control-label">사진첨부</label>
      <div class="col-sm-10">
        <button type="button" class="file_input_img_btn btn btn-info" value="사진 선택">사진 선택</button>
        <input type="file" name="imgUpload" id="imgUpload" accept="image/*" class="file_input_hidden multiple" multiple>
        <div class="preview clearfix">
          <span class="help-block">이미지 파일만 5개 이내 업로드 가능합니다.</span>
        </div>
      </div>
    </div>
    </fieldset>

    <div class="submitBtn clearfix">
      <button type="button" class="col-xs-4 col-xs-offset-2 btn btn-lg btn-warning" onclick="location.href='${pageContext.request.contextPath}/user/inquiry_list.cider'">목록</button>
      <button id="submitBtn" type="submit" class="col-xs-4 col-xs-offset-1 btn btn-lg btn-primary">문의하기</button>
    </div>
</form>
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
<script type="text/javascript">
  // 정규표현식 검사
  $(function(){  
    $("#inquiry-form").submit(function(e) {
      e.preventDefault();
      /** 이름 검사 */
      if (!regex.value('#user_name', '이름을 입력하세요.')) { return false; }
      if (!regex.kor_eng('#user_name', '이름은 한글과 영문만 입력 가능합니다.')) { return false; }
      if (!regex.min_length('#user_name', 2, '이름은 최소 2자 이상 입력 가능합니다.')) { return false; }
      if (!regex.max_length('#user_name', 20, '이름은 최대 20자 까지만 입력 가능합니다.')) { return false; }
      
      /** 연락처 검사 */
      if (!regex.value('#tel', '연락처를 입력하세요.')) { return false; }
      if (!regex.phone('#tel', '연락처가 잘못되었습니다.')) { return false; }

      /** 이메일 검사 */
      if (!regex.value('#email', '이메일을 입력하세요.')) { return false; }
      if (!regex.email('#email', '이메일 주소가 잘못되었습니다.')) { return false; }

      /** 유형 검사 */
      if (!regex.value('#sort', '유형을 선택하세요.')) { return false; }
      if (!regex.value('#title', '제목을 입력하세요.')) { return false; }
      if (!regex.value('#content', '내용을 입력하세요.')) { return false; }

      // 처리 완료
      alert("입력형식 검사 완료!!!");
    });
  });

  // 사진첨부 기능
  var input = document.querySelector('#imgUpload');
  var preview = document.querySelector('.preview');
  input.addEventListener('change', updateImageDisplay);

  const fileTypes = [
    "image/apng",
    "image/bmp",
    "image/gif",
    "image/jpeg",
    "image/pjpeg",
    "image/png",
    "image/svg+xml",
    "image/tiff",
    "image/webp",
    "image/x-icon"
  ];

  function validFileType(file) {
    return fileTypes.includes(file.type);
  }

  function updateImageDisplay() {
    const curFiles = input.files;
    const para = document.createElement('p');

    while(preview.firstChild) {
      preview.removeChild(preview.firstChild);
    }

    if(curFiles.length === 0) {
      para.textContent = '파일 선택을 취소 했습니다.';
      preview.appendChild(para);
    } else if(curFiles.length >= 6) {
      para.textContent = '5개까지 선택 가능합니다.';
      preview.appendChild(para);
    } else {
      const list = document.createElement('ol');
      preview.appendChild(list);

      for(const file of curFiles) {
        const listItem = document.createElement('li');
        const para = document.createElement('p');
        const i = document.createElement('i');
        if(validFileType(file)) {
          const image = document.createElement('img');
          image.src = URL.createObjectURL(file);

          listItem.appendChild(i);
          listItem.appendChild(image);
        } else {
          para.textContent = `File name ${file.name}: 허용된 파일타입이 아닙니다. 다시 선택하세요.`;
          listItem.appendChild(para);
        }

        list.appendChild(listItem);
      }
    }
  }

  // 사진 삭제 기능
  $(document).on('click', '.preview li i', function(){
    $(this).parent().remove();
  });

</script>