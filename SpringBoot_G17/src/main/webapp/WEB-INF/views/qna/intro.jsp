<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub04/sub_image_menu.jsp" %>

<article>

<h2>회사소개</h2>
Shoes Shop은 머시깽 머시꺵...

<h2>오시는 길</h2>

<h3>버스</h3>
-신촌 5거리

<h3>전철</h3>
-2호선 신촌역 4번 출구 200M 직전 또는 이대역 1번 출구 100M 직진


<h3>위치안내</h3>

<!-- * 카카오맵 - 지도퍼가기 -->
<!-- 1. 지도 노드 -->
<div id="daumRoughmapContainer1687325147807" class="root_daum_roughmap root_daum_roughmap_landing"></div>

<!--
	2. 설치 스크립트
	* 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
-->
<script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>

<!-- 3. 실행 스크립트 -->
<script charset="UTF-8">
	new daum.roughmap.Lander({
		"timestamp" : "1687325147807",
		"key" : "2f9tc",
		"mapWidth" : "640",
		"mapHeight" : "360"
	}).render();
</script>



</article>





<%@ include file="../include/headerfooter/footer.jsp" %>