var MyModal = (function() {
	function modal(fn) {
		this.fn = fn; //点击确定后的回调函数
		this._addClickListen();
	}
	modal.prototype = {
		show: function() {
			$('.m-modal').fadeIn(100);
			$('.m-modal').children('.m-modal-dialog').animate({
				"margin-top": "280px"
			}, 250);
		},
		_addClickListen: function() {
			var that = this;
			$(".m-modal").find('*').on("click", function(event) {
				event.stopPropagation(); //阻止事件冒泡
			});
<<<<<<< HEAD
			/*$(".m-modal,.m-modal-close,.m-btn-cancel").on("click", function(event) {
				that.hide();
			});*/
=======
//			$(".m-modal,.m-modal-close,.m-btn-cancel").on("click", function(event) {
//				that.hide();
//			});
>>>>>>> 5d5f8d663e49bc08f076943efd90867af6ee6bdc
			$(".m-btn-sure").on("click", function(event) {
				that.fn();
				that.hide();
			});
		},
		hide: function() {
			var $modal = $('.m-modal');
			$modal.children('.m-modal-dialog').animate({
				"margin-top": "-100%"
			}, 500);
			$modal.fadeOut(100);
		}

	};
	return {
		modal: modal
	}
})();