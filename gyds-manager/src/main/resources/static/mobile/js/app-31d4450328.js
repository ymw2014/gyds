'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var TabSwitch = function () {
    function TabSwitch(ele) {
        _classCallCheck(this, TabSwitch);

        this.index = 0;
        this.$ele = $(ele);

        this.$titleList = this.$ele.find('.tab-title-list');
        this.$titleItem = this.$titleList.children();
        this.$contentList = this.$ele.find('.tab-content-list');
        this.$contentItem = this.$contentList.children();
        var that = this;

        this.$titleItem.on('click', function () {
            var $this = $(this);
            var index = $this.index();
            if (index === that.getCurrentTabIndex()) {
                return false;
            }
            that.updateTitleClass($this);
            that.updateShowTab(index);
            that.setActiveIndex(index);
        });
    }

    _createClass(TabSwitch, [{
        key: 'updateTitleClass',
        value: function updateTitleClass($this) {
            this.$titleItem.removeClass('active');
            $this.addClass('active');
        }
    }, {
        key: 'getCurrentTabIndex',
        value: function getCurrentTabIndex() {
            return this.$ele.data('active-index') || 0;
        }
    }, {
        key: 'updateShowTab',
        value: function updateShowTab(index) {
            this.$contentItem.addClass('hidden').eq(index).removeClass('hidden');
        }
    }, {
        key: 'setActiveIndex',
        value: function setActiveIndex(index) {
            this.$ele.data('active-index', index);
        }
    }]);

    return TabSwitch;
}();

$(function () {
    new TabSwitch('.tab-pannel');
});
//检测是否滚动到底部
function scrollAtBottom(ele, childEle) {
    var cbAtBottom = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : function () {};
    var cbNotAtBottom = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : function () {};

    var $ele = $(ele);
    var $childEle = $ele.find(childEle);
    $ele.on('scroll', function () {
        var $childEleHeight = parseInt($childEle.height());
        var scrollTop = parseInt($ele.scrollTop());
        var $eleHeight = parseInt($ele.height());
        var offset = $childEleHeight - $eleHeight - scrollTop; //从下往上的偏移量
        if ($eleHeight + scrollTop == $childEleHeight) {
            cbAtBottom($ele, $childEle, offset);
        } else {
            cbNotAtBottom($ele, $childEle, offset);
        }
    });
}

//rem -> px 必须在window.onload后执行， 否则获取的html.fontSize会不准确
function rem2Px(size) {
    size = parseFloat(size);
    var rootFontSize = parseFloat($('html').first().css('font-size'));
    return size * rootFontSize;
}

//检测是否滚动到底部
function scrollAtBottom(ele, childEle) {
    var cbAtBottom = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : function () {};
    var cbNotAtBottom = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : function () {};

    var $ele = $(ele);
    var $childEle = $ele.find(childEle);
    $ele.on('scroll', function () {
        var $childEleHeight = parseInt($childEle.height());
        var scrollTop = parseInt($ele.scrollTop());
        var $eleHeight = parseInt($ele.height());
        var offset = $childEleHeight - $eleHeight - scrollTop; //从下往上的偏移量
        if ($eleHeight + scrollTop == $childEleHeight) {
            cbAtBottom($ele, $childEle, offset);
        } else {
            cbNotAtBottom($ele, $childEle, offset);
        }
    });
}

//rem -> px 必须在window.onload后执行， 否则获取的html.fontSize会不准确
function rem2Px(size) {
    size = parseFloat(size);
    var rootFontSize = parseFloat($('html').first().css('font-size'));
    return size * rootFontSize;
}

$(function () {
    new TabSwitch('.tab-pannel');
});

// //搜索层
function showMask(ele) {
    $('body').addClass('over-hidden');
    $('#' + ele).addClass('active');
}

function hideMask(ele) {
    $('body').removeClass('over-hidden');
    $('#' + ele).removeClass('active');
}
//搜索层 结束

//富文本处理
//判断是否隐藏
function hideArticleContent(height, ele) {
    var $ele = $(ele);
    $ele.each(function (i, v) {
        var $v = $(v);
        var $vHeight = $v.height();
        height = parseFloat(height);
        var limitHeight = rem2Px(height);
        if ($vHeight > limitHeight) {
            $v.addClass('article-mask');
        }
    });
}

function showArticleAll() {
    var $this = $(this);
    $this.parent().removeClass('article-mask');
}

function scrollToTabItem(ele, activeIndex) {
    var offset = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 100;

    var $ele = $(ele);
    var $tabTitleList = $ele.find('.tab-title-list');
    var $tabTitleItem = $tabTitleList.find('li');
    $tabTitleItem.removeClass('active').eq(activeIndex).addClass('active');
    var offsetLeft = $tabTitleItem.filter('.active').offset().left;
    $ele.find('.overflow').scrollLeft(offsetLeft - offset);
}

//初始化MagnificPopup
function initMagnificPopup() {
    var dataArr = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];

    if (!Array.isArray(dataArr)) {
        dataArr = [dataArr];
    }
    var arr = abstractPopupArr(dataArr);
    $.magnificPopup.open({
        items: arr,
        gallery: {
            enabled: true
        },
        mainClass: 'my-mfp-zoom-in',
        type: 'image', // this is default type
        callbacks: {
            beforeOpen: function beforeOpen() {
                $('body').addClass('over-hidden');
            },
            beforeClose: function beforeClose() {
                $('body').removeClass('over-hidden');
            }
        }
    });
}

//数据处理
function abstractPopupArr() {
    var dataArr = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];

    var arr = [];
    dataArr.forEach(function (v, i) {
        var cate_title = v.cate_title || '';
        var title = v.title || '';
        arr.push({
            src: v.cover_url,
            title: '<h4>' + cate_title + '</h4>\n                <p>' + title + '</p>'
        });
    });
    return arr;
}

//初始化iframe MagnificPopup
function initMagnificPopupIframe(src) {
    var title = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '';

    $.magnificPopup.open({
        items: [{
            src: src
        }],
        mainClass: 'my-mfp-zoom-in',
        type: 'iframe', // this is default type
        callbacks: {
            beforeOpen: function beforeOpen() {
                $('body').addClass('over-hidden');
            },
            open: function open() {
                $('.mfp-content').append('<h4>' + title + '</h4>');
            },
            beforeClose: function beforeClose() {
                $('body').removeClass('over-hidden');
            }
        }
    });
}

function encodeHtml(str) {
    var div = document.createElement('div');
    div.innerHTML = str;
    return div.innerText;
}

function addSpanSearch(keyword, searchClass, spanClass) {
    $(searchClass).each(function () {
        var $dom = $(this);
        if ($dom.html().indexOf(keyword) !== -1) {
            $dom.html($dom.html().replace(new RegExp(keyword, 'g'), '<span class="' + spanClass + '">' + keyword + '</span>'));
        }
    });
}

$(function () {
    $('.show-mask').on('click', function () {
        showMask($(this).data('id'));
    });
    $('.hide-mask').on('click', function () {
        hideMask($(this).data('id'));
    });

    $('.show-all').on('click', function () {
        showArticleAll.call(this);
    });
    hideArticleContent('9rem', '.article-limit');

    $('.page-back').on('click', function () {
        window.history.go(-1);
    });
});
//# sourceMappingURL=app-31d4450328.js.map
