<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/headerfooter/header.jsp"%>
<!-- <div id="main_img">
   <img src="images/main_img.jpg" style="border-radius:20px 20px 20px 20px; border:2px solid white"/>
</div> -->

<div id="main_img">
    <div id="view" style="position:relative; width:965px;  overflow:hidden;  height:350px; border-radius:20px;">
          <div id="imgs" style="position:absolute;  width:4860px;  height:350px; text-align:left;">
              <c:forEach items="${bannerList }" var="bannerVO">
                  <img src="/product_images/${bannerVO.image }" style="width:965px height:350px; margin:0">
              </c:forEach> 
     </div>
     </div>
</div>


<div id="front"><h2> New Item</h2>
   <div id="bestProduct">
      <c:forEach items="${ newProductList }" var="productVO">
         <div id="item">
            <a href="productDetail?pseq=${ productVO.pseq }">
               <img src="/product_images/${productVO.image}"/>
               <h3>
                  ${productVO.name} - <fmt:formatNumber value="${productVO.price2}" type="currency"/>
               </h3>
            </a>
         </div>
      </c:forEach>
   </div>
</div>
<div class="clear"></div>
<div id="front">
   <h2> Best Item </h2>
   <div id="bestProduct">
      <c:forEach items="${ bestProductList }" var="productVO">
         <div id="item">
            <a href="productDetail?pseq=${ productVO.pseq }">
               <img src="/product_images/${ productVO.image }"/>
               <h3> 
                  ${ productVO.name } - <fmt:formatNumber value="${ productVO.price2 }" type="currency"/>
               </h3>
            </a>
         </div>
      </c:forEach>
   </div>
</div>
<div class="clear"></div>
<%@ include file="include/headerfooter/footer.jsp"%>
<!-- static 폴더안에 jsp 넣지 못합니다. - 인식을 하지 못합니다. -->