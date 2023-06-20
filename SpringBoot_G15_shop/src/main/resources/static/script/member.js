function go_next(){
	if(document.contractFrm.okon[1].checked==true){
		alert('회원 약관에 동의 하셔야 회원으로 가입이 가능하시므니다.');
	}else{
		document.contractFrm.action="joinForm";
		document.contractFrm.submit();
	}
	
	
}