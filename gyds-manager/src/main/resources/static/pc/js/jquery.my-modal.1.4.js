var MyModal6 = (function() {
	function modal(fn) {
		this.fn = fn; //点击确定后的回调函数
		this._addClickListen();
	}
	modal.prototype = {
		show: function() {
			$('.m-modal4').fadeIn(100);
			$('.m-modal4').children('.m-modal4-dialog').animate({
				"margin-top": "30px"
			}, 250);
		},
		_addClickListen: function() {
			var that = this;
			$(".m-modal4").find('*').on("click", function(event) {
				event.stopPropagation(); //阻止事件冒泡
			});
			$(".m-modal4,.m-modal4-close,.m-btn-cancel").on("click", function(event) {
				that.hide();
			});
			$(".m-btn-sure").on("click", function(event) {
				that.fn();
				that.hide();
			});
		},
		hide: function() {
			var $modal = $('.m-modal4');
			$modal.children('.m-modal4-dialog').animate({
				"margin-top": "-100%"
			}, 500);
			$modal.fadeOut(100);
		}

	};
	return {
		modal: modal
	}
})();
// var MyModal = (function() {
// 	function modal(fn) {
// 		this.fn = fn; //点击确定后的回调函数
// 		this._addClickListen();
// 	}
// 	modal.prototype = {
// 		show: function() {
// 			$('.m-modal4').fadeIn(100);
// 			$('.m-modal4').children('.m-modal4-dialog1').animate({
// 				"margin-top": "30px"
// 			}, 250);
// 		},
// 		_addClickListen: function() {
// 			var that = this;
// 			$(".m-modal4").find('*').on("click", function(event) {
// 				event.stopPropagation(); //阻止事件冒泡
// 			});
// 			$(".m-modal4,.m-modal4-close,.m-btn-cancel").on("click", function(event) {
// 				that.hide();
// 			});
// 			$(".m-btn-sure").on("click", function(event) {
// 				that.fn();
// 				that.hide();
// 			});
// 		},
// 		hide: function() {
// 			var $modal = $('.m-modal4');
// 			$modal.children('.m-modal4-dialog1').animate({
// 				"margin-top": "-100%"
// 			}, 500);
// 			$modal.fadeOut(100);
// 		}
// 
// 	};
// 	return {
// 		modal: modal
// 	}
// })();
// var MyModal = (function() {
// 	function modal(fn) {
// 		this.fn = fn; //点击确定后的回调函数
// 		this._addClickListen();
// 	}
// 	modal.prototype = {
// 		show: function() {
// 			$('.m-modal4').fadeIn(100);
// 			$('.m-modal4').children('.m-modal4-dialog3').animate({
// 				"margin-top": "30px"
// 			}, 250);
// 		},
// 		_addClickListen: function() {
// 			var that = this;
// 			$(".m-modal4").find('*').on("click", function(event) {
// 				event.stopPropagation(); //阻止事件冒泡
// 			});
// 			$(".m-modal4,.m-modal4-close,.m-btn-cancel").on("click", function(event) {
// 				that.hide();
// 			});
// 			$(".m-btn-sure").on("click", function(event) {
// 				that.fn();
// 				that.hide();
// 			});
// 		},
// 		hide: function() {
// 			var $modal = $('.m-modal4');
// 			$modal.children('.m-modal4-dialog3').animate({
// 				"margin-top": "-100%"
// 			}, 500);
// 			$modal.fadeOut(100);
// 		}
// 
// 	};
// 	return {
// 		modal: modal
// 	}
// })();