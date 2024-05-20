<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
	<%@ include file="/WEB-INF/views/header.jsp" %>
	<script type="text/javascript">
	$(function(){
		let errorMsg = "${errorMsg}";
		if(errorMsg!=""){
			alert(errorMsg);
			errorMsg = "";
		}
		
	})// $ 끝
</script>
	
	<div class="content">
		<div class="fixed_img_col">
			<h3 class="hot">인기클래스 BEST4</h3>
			<br><br><br><br>
				<ul>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
				</ul>
		</div>
		
		<div class="fixed_img_col">
			<h3 class="hot">인기교재 BEST4</h3>
			<br><br><br><br>
				<ul>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
					<li>
						<a href="/">
							<span class="thumb">
								<img class="thumbnail_img" src="/image/9902961489dce.png">
								<br>
								<em>Category</em>
							</span>
							<strong>이미지 제목</strong>
							<p>강좌설명</p>
						</a>
					</li>
				</ul>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/footer.jsp" %>

