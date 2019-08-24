var MyModal1 = (function() {
	function modal(fn) {
		this.fn = fn; //点击确定后的回调函数
		this._addClickListen();
	}
	modal.prototype = {
		show: function() {
			$('.m-modal1').fadeIn(100);
			$('.m-modal1').children('.m-modal1-dialog').animate({
				"margin-top": "280px"
			}, 250);
		},
		_addClickListen: function() {
			var that = this;
			$(".m-modal1").find('*').on("click", function(event) {
				event.stopPropagation(); //阻止事件冒泡
			});
			$(".m-modal1,.m-modal1-close,.m-btn-cancel").on("click", function(event) {
				that.hide();
			});
			$(".m-btn-sure").on("click", function(event) {
				that.fn();
				that.hide();
			});
		},
		hide: function() {
			var $modal = $('.m-modal1');
			$modal.children('.m-modal1-dialog').animate({
				"margin-top": "-100%"
			}, 500);
			$modal.fadeOut(100);
		}

	};
	return {
		modal: modal
	}
})();