try{window.DEF_FLAG_OF_PERFLOG_JS||(window.DEF_FLAG_OF_PERFLOG_JS=!0,window.PerfLog=function(){function z(a){var b=window.pageHeaderJsonData;if(!b)return a;b=b.pageInfo;if(!b)return a;var d=b.moduleId;d&&(a+="\x26moduleId\x3d"+e(d));(d=b.pageId)&&(a+="\x26pageId\x3d"+e(d));(b=b.pageQualifier)&&(a+="\x26pageQualifier\x3d"+e(b));return a}function A(a){var b={};a.substring(a.indexOf("?")+1).split("\x26").forEach(function(a){var c=a.indexOf("\x3d"),d=0>c?a:a.substring(0,c);a=0>c?"":a.substring(c+1,a.length);
b[decodeURIComponent(d)]=decodeURIComponent(a)});return JSON.stringify(b)}function w(a){var b=!0;if(-1==a.indexOf("http://")&&-1==a.indexOf("https://"))return b;var d=window.pageHeaderJsonData,c=d&&d.defaultBaseUrl;c&&0!=a.indexOf(c)&&(c=null);c||((c=d&&d.baseUrl)&&0!=a.indexOf(c)&&(c=null),c||(b=!1));return b}function p(a){return-1==a.indexOf(PerfLog.callURL)&&-1==a.indexOf("/jsup")&&w(a)}function l(){q=w(window.location.href);var a=document.getElementById("PerfLog.eventId");x=a&&a.content;m=!0;
if(0==h)PerfLog.onPageComplete()}function B(){var a=r;if(!a)return null;var b=(new Date).getTime()-a;return 100>b?(setTimeout(function(){C()},100-b),null):a}function C(){var a=B();if(a&&m&&0==h){for(a=0;a<t.length;++a)try{t[a]()}catch(D){}t=[];if(0==h&&(a=B())&&(r=null,!E&&q)){var a=a-F,b=document.getElementsByTagName("link"),b=b&&b.length||0,d=0,c=document.getElementsByTagName("script");if(c)for(var g in c)c[g].src&&d++;g=new XMLHttpRequest;c=PerfLog.callURL;c+=0>c.indexOf("?")?"?":"\x26";c+="callId\x3d"+
G+"-"+H++;x&&(c+="\x26eventId\x3d"+e(x));c=z(c);var f=window.surj;f&&(f.TRS_TIME&&(c+="\x26TRS\x3d"+e(f.TRS_TIME)),f.TIP_TIME&&(c+="\x26TIP\x3d"+e(f.TIP_TIME)),f.TML_TIME&&(c+="\x26TML\x3d"+e(f.TML_TIME)),f.TSL_TIME&&(c+="\x26TSL\x3d"+e(f.TSL_TIME)));c+="\x26jsNum\x3d"+d+"\x26cssNum\x3d"+b+"\x26renderTime\x3d"+a;a=k.extractContextInfos();u||(b=c,d=window.performance.navigation,c=window.performance.timing,d&&c&&(d.redirectCount&&(b+="\x26RED\x3d"+e(d.redirectCount)),c.responseStart&&(b+="\x26TTB\x3d"+
e(c.responseStart-c.fetchStart)),c.domLoading&&(b+="\x26RSR\x3d"+e(c.domLoading-c.navigationStart)),c.domInteractive&&(b+="\x26DIA\x3d"+e(c.domInteractive-c.navigationStart)),c.domComplete&&(b+="\x26DCP\x3d"+e(c.domComplete-c.navigationStart))),c=b);g.open("POST",c,!0);g.setRequestHeader("Content-Type","application/x-www-form-urlencoded");g.setRequestHeader("X-Subaction",u?1:0);g.send(a);g=c;if(!(0<I++)&&window.JSON)try{var J=A(g),y=document.createElement("meta");y.id="perfLogData0";y.content=J;document.getElementsByTagName("head")[0].appendChild(y)}catch(D){window.console&&
console.log(D)}u=!0}}}function n(){k.handlePageUnload()}var e=window.encodeURIComponent,h=0,v=0,m=!1,r=null,F=(new Date).getTime(),t=[],G=Math.floor(1E10*Math.random()),H=0,x=null,q=!0,E=!1,u=!1,k={contextInfos:{},uuid:function(a){var b=0,d=Array(36),c=0,g;d[8]=d[13]=d[18]=d[23]="-";for(d[14]="4";36>b;b++)8!=b&&13!=b&&18!=b&&14!=b&&23!=b&&(2>=c&&(c=33554432+16777216*(a||Math.random())|0),c>>=4,g=19==b?c&3|8:c&15,g=10>g?48+g:97+(g-(36>g?10:36)),d[b]=String.fromCharCode(g));return d.join("")},buildContextRef:function(a){var b=
this.uuid();return"0100050001"+a+"29820909-1501-babe-face-000000000003"+b+"HTTP    ;"},initiateContextInfo:function(a){var b=this.buildContextRef(a);this.contextInfos[a]={type:0,start:(new Date).getTime()};return b},finalizeContextInfo:function(a){a=this.contextInfos[a];if(!a)return!1;a.stop=(new Date).getTime();return!0},extractContextInfos:function(a){var b=this.contextInfos,d,c,g,f="";a||(a="OPTR_INFO");for(d in b)if(c=b[d])if(g=c.stop)f&&(f+="\x26"),f+=a+"\x3d"+e(d),f+="!"+e(c.start),f+="!"+e(g),
f+="!"+e(c.type),delete b[d];return f},_getSessionStorage:function(){try{return window.sessionStorage}catch(a){}return null},handlePageUnload:function(){var a=this._getSessionStorage();if(a&&window.Date){var b=window.location;if((b=b&&b.href)&&b.indexOf){var d=b.indexOf("#");0<=d&&(b=b.substring(0,d))}b&&(a.setItem("OPTR_REQUEST_REFERRER",b),a.setItem("OPTR_REQUEST_START",(new Date).getTime()+""))}},handlePageStart:function(){var a,b=(new Date).getTime(),d=this._getSessionStorage(),c=d&&d.getItem("OPTR_REQUEST_REFERRER"),
g=d&&d.getItem("OPTR_REQUEST_START"),f,e;f=document.cookie;var h;e=f.indexOf("OptierRQUUID\x3d");if(0<=e){e+=13;h=f.indexOf(";",e);h<e&&(h=f.length);if(e=f.substring(e,h))e=decodeURIComponent(e),c&&c==document.referrer&&(f=document.getElementById("PerfLog.usePageRequestStartTime"),(f=!f||"false"!=f.content)&&(a=parseInt(g,10))),this.contextInfos[e]={type:1,start:a||0,stop:b};document.cookie="OptierRQUUID\x3d; expires\x3dThu, 01 Jan 1970 00:00:00 GMT; path\x3d/"}c&&d.removeItem("OPTR_REQUEST_REFERRER");
g&&d.removeItem("OPTR_REQUEST_START")}},I=0;XMLHttpRequest.postreadystatechange=function(){if(4==this.readyState&&p(this.url)){if(!q||this.doesAllowCORS&&this.doesAllowCORS()||k.finalizeContextInfo(this.rquuid),h--,0==h&&m)PerfLog.onPageComplete()}else 4==this.readyState&&v--};XMLHttpRequest.onopen=function(a,b,d){this.url=b};XMLHttpRequest.onsend=function(a){if(p(this.url)){if(q&&(!this.doesAllowCORS||!this.doesAllowCORS())){a=k.uuid();var b=k.initiateContextInfo(a);this.rquuid=a;this.setRequestHeader("OPTR_CXT",
b);this.setRequestHeader("X-Subaction",u?1:0)}0==h&&m&&!r&&(F=(new Date).getTime());h++}else v++};XMLHttpRequest.onabort=function(){if(p(this.url)){if(h--,0==h&&m)PerfLog.onPageComplete()}else v--};return{callURL:"/perfLogServlet",addOnloadHook:function(a){a&&(PerfLog.callURL=a);k.handlePageStart();if(window.SFDOMEvent)SFDOMEvent.addOnDomReadyListener(l),SFDOMEvent.addEventListener(window,"beforeunload",n);else if(window.YAHOO&&YAHOO.util&&YAHOO.util.Event&&YAHOO.util.Event.onDOMReady)YAHOO.util.Event.onDOMReady(l),
YAHOO.util.Event.addListener(window,"beforeunload",n);else if(window.jQuery)jQuery(l),jQuery(window).bind("beforeunload",n);else{var b=window.onload;window.onload="function"!=typeof b?l:function(){b&&b();l()};var d=window.onbeforeunload;window.onbeforeunload="function"!=typeof d?n:function(){d&&d();n()}}},getPendingAjaxCount:function(){return h+v},pageUnload:function(){E=!0},pageComplete:function(a){t.push(a)},onPageComplete:function(){r=(new Date).getTime();C()},_test:{uuid:k.uuid,parseUrltoJsonString:A,
isBaseDomain:w,isPerfMeasureableUrl:p}}}())}catch(z){};