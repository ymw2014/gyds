$(function () {
		var num=0;
		var img= $('.lun1 img');
		$('.right1').click(function () {
			if(!$('.lun1').is(':animated')){
				num++;
				$('.lun1').animate({
					left:-num*765+'px'
				}, function () {
					if(num==img.length-1){
						num=0;
						$('.lun1').css('left','0px')
					}
				});
			}
			changeNum(num);
		});
		$('.left1').click(function () {
			if(!$('.lun1').is(':animated')) {
				if (num==0) {
					num = img.length-1;
					$('.lun1').css('left','3060px')
				}
				num--;
				$('.lun1').animate({
					left: -num * 765 + 'px'
				})
			}
		});
		function changeNum(num){
			if(num==$('.lun1 img').length-1){
				num=0;
			}
			$('.list1 li').removeClass('select1').eq(num).addClass('select1')
		}
		timer=setInterval(function () {
			$('.right1').click()
		},4000);

		$('.list1 li').click(function () {
			if(!$('.lun1 img').is(':animated')){
				var now=$(this).index();
				if(now>num){
					$('.lun1').css('left',-(now-1)*765+'px').animate({'left':-now*765+'px'},2000)
				}else if(now<num){
					$('.lun1').css('left',-(now+1)*765+'px').animate({'left':-now*765+'px'},2000)
				}
				num=now;
				changeNum(num);
			}

		})
		timer=setInterval(function(){
			$("#btnR").click()
		},4000)
		$("#lunbo").hover(function(){
			clearInterval(timer)
		},function(){
			timer=setInterval(function(){
				$("#btnR").click()
			},4000)
		})

	});
	$(function () {
			var num=0;
			var img= $('.lun img');
			$('.right').click(function () {
				if(!$('.lun').is(':animated')){
					num++;
					$('.lun').animate({
						left:-num*612+'px'
					}, function () {
						if(num==img.length-1){
							num=0;
							$('.lun').css('left','0px')
						}
					});
				}
				changeNum(num);
			});
			$('.left').click(function () {
				if(!$('.lun').is(':animated')) {
					if (num==0) {
						num = img.length-1;
						$('.lun').css('left', '1836px')
					}
					num--;
					$('.lun').animate({
						left: -num * 612 + 'px'
					})
				}
			});
			function changeNum(num){
				if(num==$('.lun img').length-1){
					num=0;
				}
				$('.list li').removeClass('select').eq(num).addClass('select')
			}
			timer=setInterval(function () {
				$('.right').click()
			},3000);
	
			$('.list li').click(function () {
				if(!$('.lun img').is(':animated')){
					var now=$(this).index();
					if(now>num){
						$('.lun').css('left',-(now-1)*612+'px').animate({'left':-now*612+'px'},1000)
					}else if(now<num){
						$('.lun').css('left',-(now+1)*612+'px').animate({'left':-now*612+'px'},1000)
					}
					num=now;
					changeNum(num);
				}
	
			})
			timer=setInterval(function(){
				$("#btnR").click()
			},3000)
			$("#lunbo").hover(function(){
				clearInterval(timer)
			},function(){
				timer=setInterval(function(){
					$("#btnR").click()
				},3000)
			})
	
		})
	
	
