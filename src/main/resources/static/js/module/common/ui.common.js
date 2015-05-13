var StrUtil = {
	toInt:function(num){
		if(num == undefined || num == null || num == '' || num == 'NaN'){
			num = '0';
		}
		return parseInt(num);
	}
}

/*
 * 功能：页面通用的UI操作
 */
ui = window.ui ||{
	go:function(url,timeout){
		if(url != null && url != ''){
			timeout = timeout || 1800;
			setTimeout(function(){
				document.location.href = url;
			},timeout);
		}
	},
	reload:function(){
		setTimeout(function(){
			document.location.reload();
		},1800);
	},
	confirm:function(msg,myCallBack,appendHtml){
		var content = '<div class="alert alert-warning text-center"><i class="glyphicon glyphicon-warning-sign fa fa-warning bigger-120"></i> <strong>'+msg+'</strong></div>';
		if(appendHtml!=null&&appendHtml!=''){
			content = content + appendHtml;
		}
		var uibox = $.scojs_confirm({
			  content: content,
			  action: myCallBack
		});
		uibox.show();
		
		//see sco.confirm.js --> id="uiBoxCancellBtn"
		$('#uiBoxCancellBtn').click(function(){
			uibox.destroy();
		});
		$('#uiBoxYeapBtn').click(function(){
			uibox.destroy();
		});
	},
	msg:function(msg,type,callBack){
		if(type === undefined || type == 0){
			msg = '<div><i class="glyphicon glyphicon-remove-circle fa-remove-circle"></i> '+msg || '操作失败！'+'</div>';
			$.scojs_message(msg, $.scojs_message.TYPE_ERROR);
			//smoke.signal(msg,2300);
		}
		if(type == 1){
			msg = '<div><i class="glyphicon glyphicon-ok-circle  fa-ok-circle"></i> '+msg || '操作成功！'+'</div>';
			$.scojs_message(msg, $.scojs_message.TYPE_OK);
			//smoke.signal(msg,2300);
		}
		if(type == 3){
			msg = '<div><i class="glyphicon glyphicon-exclamation-sign  fa-exclamation-sign"></i> '+msg || '操作信息提示！'+'</div>';
			$.scojs_message(msg, $.scojs_message.TYPE_INFO);
			//smoke.signal(msg,2300);
		}
		if(type == 4){
			msg = '<div><i class="glyphicon glyphicon-warning-sign  fa fa-warning"></i> '+msg || '操作警告！'+'</div>';
			$.scojs_message(msg, $.scojs_message.TYPE_WARNING);
			//smoke.signal(msg,2300);
		}
		if(callBack != undefined){
			var self = this;
			callBack.call(self);
		}
	},
	msgSuccess:function(msg,callBack){
		this.msg(msg, 1, callBack);
	},
	msgError:function(msg,callBack){
		this.msg(msg, 0, callBack);
	},
	msgInfo:function(msg,callBack){
		this.msg(msg, 3, callBack);
	},
	msgWarning:function(msg,callBack){
		this.msg(msg, 4, callBack);
	},
	box:function(options){
		var defaults = {
			title:'网页信息',
			content:'',
			size: '',   //sm , lg
			backdrop:true,
			keyboard:true,
			show:true,
			remote:false,  //如果是，则在remote这里填写URL
			method:'GET',
			param:{},    //当remote有值时，需要传递的参数
			btn:''
		};
		var opts = $.extend(defaults, options);
		var tpl = '<div id="myUIBox" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">'+
		'  <div  id="myUIBoxWrapper" class="modal-dialog">'+
		'   <div class="modal-content">'+
		'		<div class="modal-header">'+
		'		  <button type="button" class="close" data-dismiss="modal" aria-hidden="true" title="关闭">&times;</button>'+
		'		  <h4 class="modal-title" id="myModalLabel">'+opts.title+'</h4>'+
		'		</div>'+
		'		<div class="modal-body" id="myUIBoxContent">'+opts.content+'</div>'+
		'			<div class="modal-footer">'+
		'				'+opts.btn+'<button type="button" class="btn btn-danger btn-sm" data-dismiss="modal" id="myUIBoxCloseBtn">关闭</button>'+
		'			</div>'+
		' 	</div>'+
		'  </div>'+
		'</div>';
		$("body").append(tpl);
		
		if(opts.size != ''){
			var sizeClass = '';
			if(opts.size == 'sm' ){
				sizeClass = 'modal-sm';
			}
			if(opts.size == 'mid' ){
				sizeClass = '';
			}
			if(opts.size == 'lg' ) {
				sizeClass = 'modal-lg';
			}
			$('#myUIBoxWrapper').addClass( sizeClass );
		}
		
		if(opts.remote != ''){
			$('#myUIBoxContent').html( '<h4 class="text-center"><i class="ace-icon fa fa-spinner fa-spin"></i> loading ...</h4>' );
			
			$.ajax({
			   type: opts.method,
			   dataType:"html",
			   cache: false,
			   url: opts.remote,
			   data: opts.param,
			   success: function( htmlContent ){
				   $('#myUIBoxContent').html( htmlContent );
			   },
			   error:function(){
				   var erroMsg = '<div clsss="alert alert-danger">信息加载错误</div>';
				   $('#myUIBoxContent').html( erroMsg );
			   }
			});
			
			opts.remote  = false;
			opts.backdrop  = 'static';
		}
		
		//when the box is close,then remove the box id from body
		$('#myUIBox').on('hidden.bs.modal', function (e) {
			$('#myUIBox').remove();
		})
		
		$('#myUIBox').modal(opts);
		$( "#myUIBox" ).draggable({ handle: ".modal-header"});
	},
	boxClose:function(){
		$('#myUIBoxCloseBtn').click();
	},
	fixNull:function(str){
		return str || '';
	}
};

//字符串长度-中文和全角符号为1，英文、数字和半角为0.5
var getLength = function(str, shortUrl) {
	if (true == shortUrl) {
		return Math.ceil(str.replace(/((news|telnet|nttp|file|http|ftp|https):\/\/){1}(([-A-Za-z0-9]+(\.[-A-Za-z0-9]+)*(\.[-A-Za-z]{2,5}))|([0-9]{1,3}(\.[0-9]{1,3}){3}))(:[0-9]*)?(\/[-A-Za-z0-9_\$\.\+\!\*\(\),;:@&=\?\/~\#\%]*)*/ig, 'http://goo.gl/fkKB ')
							.replace(/^\s+|\s+$/ig,'').replace(/[^\x00-\xff]/ig,'xx').length/2);
	} else {
		return Math.ceil(str.replace(/^\s+|\s+$/ig,'').replace(/[^\x00-\xff]/ig,'xx').length/2);
	}
};

var subStr = function (str, len) {
    if(!str) { return ''; }
        len = len > 0 ? len*2 : 280;
    var count = 0,	//计数：中文2字节，英文1字节
        temp = '';  //临时字符串
    for (var i = 0;i < str.length;i ++) {
    	if (str.charCodeAt(i) > 255) {
        	count += 2;
        } else {
        	count ++;
        }
        //如果增加计数后长度大于限定长度，就直接返回临时字符串
        if(count > len) { return temp; }
        //将当前内容加到临时字符串
         temp += str.charAt(i);
    }
    return str;
};

/**
 * 生成UUID
 */
function getUUID() {
    var d = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (d + Math.random()*16)%16 | 0;
        d = Math.floor(d/16);
        return (c=='x' ? r : (r&0x7|0x8)).toString(16);
    });
    return uuid;
};
/*
 * D 基本DOM操作
 * $(ID)
 * DC(tn) TagName
 * EA(a,b,c,e)
 * ER(a,b,c)
 * BS()
 * FF
 */
var D = {
	$:function(ID){
		return document.getElementById(ID)
	},
	DC:function(tn){
		return document.createElement(tn);
	},
    EA:function(a, b, c, e) {
        if (a.addEventListener) {
            if (b == "mousewheel") b = "DOMMouseScroll";
            a.addEventListener(b, c, e);
            return true
        } else return a.attachEvent ? a.attachEvent("on" + b, c) : false
    },
    ER:function(a, b, c) {
        if (a.removeEventListener) {
            a.removeEventListener(b, c, false);
            return true
        } else return a.detachEvent ? a.detachEvent("on" + b, c) : false
    },
	BS:function(){
		var db=document.body,
			dd=document.documentElement,
			top = db.scrollTop+dd.scrollTop;
			left = db.scrollLeft+dd.scrollLeft;
		return { 'top':top , 'left':left };
	},
	
	FF:(function(){
		var ua=navigator.userAgent.toLowerCase();
		return /firefox\/([\d\.]+)/.test(ua);
	})()
};


/**
 * checkAll 操作
 */
$(function() {  
	$('table th input:checkbox').on('click' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
			
	});
});

var checkAll = {
  isChecked:function(){
	  var $chkarry = $('input:checked').not($('#checkedAll'));
	  if( $chkarry != null && $chkarry.length > 0){
		  return true;
	  }else{
		  return false;
	  }
  },
  getIdsAsArr:function(){
	  var $chkarry = $('input:checked').not($('#checkedAll'));
	  var params = new Array();
	  for(var i=0;i<$chkarry.length;i++){
		  params.push( $chkarry[i].value );
	  }
	  return params;
  },
  getIdsAsStr:function(){
	  var $chkarry = $('input:checked').not($('#checkedAll'));
	  var params = '';
	  for(var i=0;i<$chkarry.length;i++){
		  params += $chkarry[i].value + ",";
	  }
	  params = params.substring(0,params.length - 1);
	  return params;
  }
}