(function(l){var m=function(b){b=document.getElementById(b);var a=null;b&&(a=b.content);return a},n=function(){for(var b=window.pageHeaderJsonData,a=(b=b&&b.userInfo)&&b.normalizedLocale||m("normalizedLocale")||m("locale")||null,b=/^((?:[A-Z]{2,3}(?:-[A-Z]{3}){0,3})|[A-Z]{4}|[A-Z]{5,8})(?:-([A-Z]{4}))?(?:-([A-Z]{2}|[0-9]{3}))?((?:-[0-9A-Z]{5,8}|-[0-9][0-9A-Z]{3})*)((?:-[0-9A-WYZ](?:-[0-9A-Z]{2,8})+)*)(?:-(X(?:-[0-9A-Z]{1,8})+))?$/i,d=/^(.*)[_\-][^_\-]+/;a&&!b.test(a.replace("_","-"));)a=(a=d.exec(a))?
a[1]:null;return a},p=function(b){var a;return(a=/^(.*_[^_\/]*\/)([^_]*\/[^_]*)(_[^\/]*|)(\.\w+)$/.exec(b))?a[2]+a[4]:null},q=function(){for(var b,a=0;a<arguments.length;a++){var d;d=(d=arguments[a])?/([^-_]*)(?:[-_].*|)/.exec(d)[1]:"";if(0==a)b=d;else if(b!=d)return!1}return!0},r=function(b){if(/^(https?:\/\/[^\/]*|\/\/[^\/]*|)\/messagebundle\//.test(b)){var a=/(.*\/[^_]*)(_[^\/]*|)(\.\w+)$/.exec(b);if(a){var d=a[3];if(".properties"==d){var e=a[2];e&&(e=e.substring(1));var c=n();c&&(c=c.replace(/-/g,
"_"),e!=c&&q(e,c)&&(b=a[1]+(c?"_":"")+c+d))}}}return b},u=function(b){var a=document.querySelectorAll("meta[id^\x3dresourcemapping]");if(a){var d=a.length;if(d)for(var e=0;e<d;e++){var c=a[e],f=c.content;if(f){b=b||{};var h=c.id;if(c=/\:(.*)$/.exec(h))h=c[1];(c=p(h))&&(b[c]=f)}}}if(b){var a=/^(.*)\.properties/,g;for(g in b)if(d=b[g],c=a.exec(g))e=c[1],!b[e]&&(c=a.exec(d))&&(b[e]=c[1])}g=b;c=window.pageHeaderJsonData;if(a=g&&c){a:{a=!1;if(d=window.pageHeaderJsonData)a=d.enableBaseDomainCheck;a||(a=
(a=document.getElementById("bizXRemotableHeader"))&&"true"==a.content);if(a&&(d=window.pageHeaderJsonData)){document.getElementById("bizXRemotableHeader");a=d.baseUrl;d=d.defaultBaseUrl;f=void 0;f=window.location;e=f.origin;e||(e=f.protocol+"//"+f.hostname,(f=f.port)&&(e+=":"+f));a=!a||a==e||d==e;break a}a=!0}a=!a}if(a){var c=c.baseUrl,k;for(k in g)a=g[k],"/"==a.charAt(0)&&"/"!=a.charAt(1)&&(g[k]=c+a)}return b},t=function(b){var a=sap.ui.getCore().getConfiguration();return new Promise(function(d,
e){var c=function(a,b){"string"===typeof a?b=b||{}:(b=a||{},a=b.name);b.url=r(b.url);var c=[b];"string"==typeof a&&c.splice(0,0,a);return f.apply(this,c)},f,h=u(window._pathMappings);if(0<=a.getVersion().compareTo("1.58")){h&&sap.ui.loader.config({paths:h});var g=function(a){f=a.loadResource;a.loadResource=c;d()};b?sap.ui.require(["sap/base/util/LoaderExtensions"],g):g(sap.ui.requireSync("sap/base/util/LoaderExtensions"))}else g=function(a){var b=a.sap;if(f=b.loadResource)b.loadResource=c;if(h){var e=
b.resources;b.resources=a.extend(function(a){var b=p(a.url);b&&(b=h[b])&&(a.url=b);return e.apply(this,arguments)},e)}d()},b?sap.ui.require(["jquery.sap.global"],g):g(sap.ui.requireSync("jquery.sap.global"))})};window._LocaleBootstrap={upgrade:t,isSameLanguage:q,rewriteBundleUrl:r};(function(){var b=l["sap-ui-config"]=l["sap-ui-config"]||{},a=b["xx-bootTask"],d=n();d&&(b.language=d);b["xx-supportedLanguages"]=["default"];b["xx-bootTask"]=function(b){var c="true"==""+window["sap-ui-config"].async,
d=t(c);b=a?a.bind(null,b):b;c?d.then(b):b()}})()})(window);