/*
 !! BEGIN /ui/login/js/bizx/LoginForm.js !!! !! BEGIN /ui/login/js/bizx/BizXLogin.view.js !!! !! BEGIN /ui/login/js/bizx/BizXLogin.controller.js !!! !! BEGIN /ui/login/js/bizx/BizXLogin.bootstrap.js !!!*/
(function(a){a.sap.require("sap.ui.core.Control");a.sap.declare("sap.sf.login.bizx.LoginForm");a.sap.getObject("sap.ui.core.Control").extend("sap.sf.login.bizx.LoginForm",{metadata:{properties:{company:"string",hiddenProperties:"any",action:"string",method:"string"},aggregations:{content:{type:"sap.ui.core.Control",multiple:!0}}},renderer:function(g,c){g.write("\x3cform");g.writeControlData(c);g.addClass("bizLoginForm");g.writeClasses();g.writeAttributeEscaped("action",c.getAction());g.writeAttributeEscaped("method",
c.getMethod());g.writeAttribute("autocomplete","off");g.write("\x3e");a.each(c.getHiddenProperties()||{},function(a,c){g.write('\x3cinput type\x3d"hidden"');g.writeAttributeEscaped("name",a);g.writeAttributeEscaped("value",c);g.write("\x3e")});g.write('\x3cinput type\x3d"hidden" name\x3d"company"');g.writeAttributeEscaped("value",c.getCompany());g.write("\x3e");a.each(c.getContent()||[],function(){g.renderControl(this)});g.write("\x3c/form\x3e")},onAfterRendering:function(){this.$().find("button").attr("type",
"button")}})})(jQuery);
(function(a){a.sap.declare("sap/sf/surj/bizXLogin/BizXLogin.view.js");a.sap.require("sap.ui.core.IconPool");a.sap.require("sap.ui.core.HTML");a.sap.require("sap.ui.core.Icon");a.sap.require("sap.ui.layout.HorizontalLayout");a.sap.require("sap.ui.layout.VerticalLayout");a.sap.require("sap.sf.surj.shell.controls.Container");a.sap.require("sap.sf.surj.shell.core.BizXResourceBundle");a.sap.require("sap.sf.surj.shell.util.SuppIconPool");a.sap.require("sap.m.library");var g=sap.ui.getCore(),c,l=function(){return c=
c||g.getLibraryResourceBundle("sap.sf.login.i18n")},m=function(a){return(new sap.ui.layout.VerticalLayout({content:[(new sap.m.Image({alt:"{/customLogoAlt}",decorative:!1,densityAware:!1,src:"{/customLogoURL}",tooltip:"{/customLogoTooltip}",load:[a.onLogoImageLoad,a],error:[a.onLogoImageError,a]})).addStyleClass("globalLoginLogoImg")]})).addStyleClass("globalLoginLogoContainer")};sap.ui.jsview("sap.sf.login.bizx.BizXLogin",{createContent:function(b){this.addStyleClass("globalLogin").addStyleClass("bizxLogin");
this.setDisplayBlock(!0);var k=a("#footer").html()||"",n=a("#dynamic_copyright").html();n&&(k=n+k);var k='\x3cdiv class\x3d"globalLoginFooter bizXFooter"\x3e'+k+"\x3c/div\x3e",p=a("#dynamic_header").html(),p=p?'\x3cdiv class\x3d"dynamic_header"\x3e'+p+"\x3c/div\x3e":'\x3cdiv class\x3d"dynamic_header" style\x3d"display:none"\x3e\x3c/div\x3e',n=new sap.ui.core.HTML;n.setContent(p);p=new sap.ui.core.HTML;p.setContent(k);return[new sap.ui.core.HTML({content:'\x3cdiv class\x3d"globalLoginBgOverlay"\x3e\x3c/div\x3e'}),
n,new sap.ui.core.HTML({content:'\x3cspan class\x3d"globalLoginLogoURL" style\x3d"display:none"\x3e\x3c/span\x3e'}),(new sap.m.App("bizXLoginApp",{initialPage:b.getInitialPage(),pages:[this.createCompanyCheckPage(b),this.createCompanyEntryPage(b),this.createUserLoginPage(b),this.createHelpMessagePage(b),this.createCompanyHelpPage(b),this.createUsernameHelpPage(b),this.createPasswordHelpPage(b)],afterNavigate:[b.afterNavigate,b]})).addStyleClass("loginApp"),p]},createCompanyCheckPage:function(a){return(new sap.m.Page("companyCheck",
{showHeader:!1,showFooter:!1,content:[(new sap.ui.layout.VerticalLayout({content:[new sap.m.BusyIndicator({text:l().getText("COMMON_loading")||"Loading..."})]})).addStyleClass("mainAppContent")]})).addStyleClass("companyCheckPage")},createCompanyEntryPage:function(a){var b=l();return new sap.m.Page("companyEntry",{showHeader:!1,showFooter:!1,content:[(new sap.ui.layout.VerticalLayout({content:[(new sap.m.Image({src:IMAGES["/ui/login/img/logos/SF_Logo_Lg-White.png"],load:[a.onLogoImageLoad,a],error:[a.onLogoImageError,
a]})).addStyleClass("bizxLogo"),new sap.ui.core.HTML({content:"{/companyEntryMessage}",visible:{parts:[{path:"/companyEntryMessage"}],formatter:function(a){return!!a}}}),(new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.layout.VerticalLayout({content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://SF-supp-icons/building high",useIconTooltip:!1})).addStyleClass("inputIcon"),(new sap.m.Input({type:"Text",value:"{/company}",placeholder:b.getText("COMMON_LOGIN_ENTER_COMPANY_ID"),
submit:[a.submitCompanyEntry,a]})).addStyleClass("inputWithHelp").addStyleClass("inputWithInlineButton")]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs"),(new sap.m.Button({type:sap.m.ButtonType.Emphasized,icon:"sap-icon://navigation-right-arrow",tooltip:b.getText("COMMON_LOGIN_COMPANY_ID_SUBMIT_BTN"),press:[a.submitCompanyEntry,a]})).addStyleClass("inlineButton")]})).addStyleClass("hasInlineButton"),(new sap.m.Button({type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",
tooltip:b.getText("COMMON_LOGIN_COMPANY_ID_HELP_BTN"),press:[a.showCompanyIdHelp,a]})).addStyleClass("helpButtonAlign").addStyleClass("withInlineButton")]})).addStyleClass("mainAppContent")]})},createUserLoginPage:function(b){var k=l(),n=new sap.sf.login.bizx.LoginForm("loginForm",{method:"POST",action:"/login",hiddenProperties:"{/parameters/hiddenInputs}",company:"{/company}",content:[(new sap.ui.layout.VerticalLayout({content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://person-placeholder",
useIconTooltip:!1})).addStyleClass("inputIcon"),(new sap.m.Input({type:"Text",name:"username",value:"{/username}",placeholder:k.getText("COMMON_Username")})).addStyleClass("inputWithHelp").addStyleClass("username")]}),new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://SF-supp-icons/key",useIconTooltip:!1})).addStyleClass("inputIcon"),(new sap.m.Input({type:"Password",name:"password",value:"{/password}",placeholder:k.getText("COMMON_LOGIN_ENTER_PASSWORD")})).addStyleClass("inputWithHelp").addStyleClass("password")]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs"),
(new sap.m.Button({type:sap.m.ButtonType.Emphasized,text:k.getText("COMMON_LOGIN"),press:[b.submitUserLogin,b]})).addStyleClass("fullButton"),new sap.ui.core.HTML({content:'\x3cinput type\x3d"submit" style\x3d"position: absolute; left: -9999px; width: 1px; height: 1px;" tabindex\x3d"-1" /\x3e'})]});n.addEventDelegate({onAfterRendering:function(k){k.srcControl.$().submit(a.proxy(b.onLoginFormSubmit,b))}});return new sap.m.Page("login",{showHeader:!1,showFooter:!1,content:(new sap.ui.layout.VerticalLayout({content:[m(b),
new sap.ui.core.HTML({content:"{/loginMessage}",visible:{parts:[{path:"/loginMessage"}],formatter:function(a){return!!a}}}),n,(new sap.m.Button({visible:{parts:[{path:"/validCompany"}],formatter:function(a){return!a}},type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",tooltip:k.getText("COMMON_LOGIN_COMPANY_ID_HELP_BTN"),press:[b.showCompanyIdHelp,b]})).addStyleClass("helpButtonAlign"),(new sap.m.Button({type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",tooltip:k.getText("COMMON_LOGIN_USERNAME_HELP_BTN"),
press:[b.showUsernameHelp,b]})).addStyleClass("helpButtonAlign"),(new sap.m.Button({type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",tooltip:k.getText("COMMON_LOGIN_PASSWORD_HELP_BTN"),press:[b.showPasswordHelp,b]})).addStyleClass("helpButtonAlign"),(new sap.sf.surj.shell.controls.Container({visible:"{/mobileFeature}",content:[(new sap.ui.core.Icon({src:"sap-icon://iphone",useIconTooltip:!1})).addStyleClass("mobileActivationIcon"),(new sap.m.Link({text:k.getText("COMMON_QRCODE_ACTIVATION_LINK"),
press:[b.showQRCode,b]})).addStyleClass("text mobileActivationLink")]})).addStyleClass("mobileLinkContainer")]})).addStyleClass("mainAppContent")})},createHelpMessagePage:function(a){var b=l();return new sap.m.Page("helpMessage",{showHeader:!1,showFooter:!1,content:[(new sap.ui.layout.VerticalLayout({content:[m(a),new sap.ui.core.HTML({content:"{/helpMessage}"}),(new sap.m.Button({text:b.getText("ADMIN_BACKTOLOGIN"),tooltip:b.getText("ADMIN_BACKTOLOGIN"),press:[a.backToLogin,a]})).addStyleClass("minHalfButton")]})).addStyleClass("mainAppContent")]})},
createCompanyHelpPage:function(a){var b=l();return new sap.m.Page("companyHelp",{showHeader:!1,showFooter:!1,content:[(new sap.ui.layout.VerticalLayout({content:[m(a),new sap.ui.core.HTML({content:'\x3cdiv class\x3d"helpTitle"\x3e'+b.getText("ADMIN_COMPANYIDHELP_FORGOT_TITTLE")+"\x3c/div\x3e"}),new sap.ui.core.HTML({content:'\x3cdiv class\x3d"helpText"\x3e'+b.getText("ADMIN_COMPANYIDHELP_FORGOT_DESC")+"\x3c/div\x3e"}),new sap.ui.core.HTML({content:'\x3cdiv class\x3d"lastHelpText helpText"\x3e'+b.getText("ADMIN_COMPANYIDHELP_FORGOT_TIPS",
["//www.successfactors.com/en_us/support/faq.html"])+"\x3c/div\x3e"}),(new sap.m.Button({text:b.getText("COMMON_Ok"),press:[a.hideCompanyIdHelp,a]})).addStyleClass("halfButton")]})).addStyleClass("mainAppContent")]})},createUsernameHelpPage:function(a){var b=l();return new sap.m.Page("usernameHelp",{showHeader:!1,showFooter:!1,content:[(new sap.ui.layout.VerticalLayout({content:[m(a),new sap.ui.core.HTML({content:'\x3cdiv class\x3d"helpTitle"\x3e'+b.getText("ADMIN_USERNAME_FORGOT_TITTLE")+"\x3c/div\x3e"}),
new sap.ui.core.HTML({content:{parts:[{path:"/validCompany"},{path:"/disableForgetUsername"}],formatter:function(a,k){return'\x3cdiv class\x3d"lastHelpText helpText"\x3e'+(k?b.getText("ADMIN_USERNAME_FORGOT_DISABLED",["//www.successfactors.com/en_us/support/faq.html"]):a?b.getText("ADMIN_USERNAME_FORGOT_DESC"):b.getText("ADMIN_USERNAME_FORGOT_DESC_NOCOMP"))+"\x3c/div\x3e"}}}),new sap.sf.surj.shell.controls.Container({visible:{parts:[{path:"/disableForgetUsername"}],formatter:function(a){return!a}},
content:[(new sap.ui.layout.VerticalLayout({visible:{parts:[{path:"/validCompany"}],formatter:function(a){return!a}},content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://SF-supp-icons/building high",useIconTooltip:!1})).addStyleClass("inputIcon"),(new sap.m.Input({type:"Text",placeholder:b.getText("COMMON_LOGIN_ENTER_COMPANY_ID"),value:"{/company}",submit:[a.submitUsernameHelp,a]})).addStyleClass("inputWithHelp")]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs"),
(new sap.ui.layout.VerticalLayout({content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://email",useIconTooltip:!1})).addStyleClass("inputIcon"),new sap.m.Input({type:"Email",placeholder:b.getText("COMMON_RECRUITING_EDIT_EMAIL_INSTRUC"),value:"{/usernameHelpEmail}",submit:[a.submitUsernameHelp,a]})]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs"),(new sap.m.Bar({contentRight:[(new sap.m.Button({text:b.getText("COMMON_Cancel"),tooltip:b.getText("COMMON_Cancel"),
press:[a.cancelUsernameHelp,a]})).addStyleClass("halfButton").addStyleClass("startButton"),(new sap.m.Button({type:sap.m.ButtonType.Emphasized,text:b.getText("PASSWORDHELP_EMAILME"),tooltip:b.getText("PASSWORDHELP_EMAILME"),press:[a.submitUsernameHelp,a]})).addStyleClass("halfButton").addStyleClass("endButton")]})).addStyleClass("loginFormButtons")]}),(new sap.m.Bar({visible:{parts:[{path:"/disableForgetUsername"}],formatter:function(a){return!!a}},contentRight:[(new sap.m.Button({text:b.getText("COMMON_Ok"),
tooltip:b.getText("COMMON_Ok"),press:[a.cancelUsernameHelp,a]})).addStyleClass("halfButton")]})).addStyleClass("loginFormButtons"),(new sap.m.Button({visible:{parts:[{path:"/validCompany"},{path:"/disableForgetUsername"}],formatter:function(a,b){return!a&&!b}},type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",tooltip:b.getText("COMMON_LOGIN_COMPANY_ID_HELP_BTN"),press:[a.showCompanyIdHelp,a]})).addStyleClass("helpButtonAlign")]})).addStyleClass("mainAppContent")]})},createPasswordHelpPage:function(a){var b=
l();return new sap.m.Page("passwordHelp",{showHeader:!1,showFooter:!1,content:[(new sap.ui.layout.VerticalLayout({content:[m(a),new sap.ui.core.HTML({content:'\x3cdiv class\x3d"helpTitle"\x3e'+b.getText("ADMIN_PASSWORDHELP_FORGOT_TITTLE")+"\x3c/div\x3e"}),new sap.ui.core.HTML({content:{parts:[{path:"/validCompany"},{path:"/retByEmail"},{path:"/disableForgetPassword"}],formatter:function(a,c,g){return'\x3cdiv class\x3d"lastHelpText helpText"\x3e'+(g?b.getText("ADMIN_PASSWORD_FORGOT_DISABLED",["//www.successfactors.com/en_us/support/faq.html"]):
a?("string"==typeof c?"true"===c:c)?b.getText("ADMIN_PASSWORDHELP_FORGOT_DESC_WITHEMAIL"):b.getText("ADMIN_PASSWORDHELP_FORGOT_DESC"):b.getText("ADMIN_PASSWORDHELP_FORGOT_DESC_NOCOMP"))+"\x3c/div\x3e"}}}),new sap.sf.surj.shell.controls.Container({visible:{parts:[{path:"/disableForgetPassword"}],formatter:function(a){return!a}},content:[(new sap.ui.layout.VerticalLayout({visible:{parts:[{path:"/validCompany"},{path:"/companyIdLocked"}],formatter:function(a,b){return!a&&!b}},content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://SF-supp-icons/building high",
useIconTooltip:!1})).addStyleClass("inputIcon"),(new sap.m.Input({type:"Text",placeholder:b.getText("COMMON_LOGIN_ENTER_COMPANY_ID"),value:"{/company}",submit:[a.submitUsernameHelp,a]})).addStyleClass("inputWithHelp")]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs"),(new sap.ui.layout.VerticalLayout({content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://person-placeholder"})).addStyleClass("inputIcon"),(new sap.m.Input({type:"Text",
placeholder:b.getText("COMMON_LOGIN_ENTER_USERNAME"),value:"{/passwordHelpUsername}",submit:[a.submitPasswordHelp,a]})).addStyleClass("inputWithHelp")]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs"),new sap.sf.surj.shell.controls.Container({visible:{parts:[{path:"/validCompany"},{path:"/retByEmail"}],formatter:function(a,b){"string"==typeof b&&(b="true"===b);return!!a&&!!b}},content:[new sap.ui.core.HTML({content:'\x3cdiv class\x3d"passwordHelpOr"\x3e'+b.getText("PASSWORDHELP_OR")+
"\x3c/div\x3e"}),(new sap.ui.layout.VerticalLayout({content:[new sap.ui.layout.HorizontalLayout({content:[(new sap.ui.core.Icon({src:"sap-icon://email"})).addStyleClass("inputIcon"),new sap.m.Input({type:"Email",placeholder:b.getText("COMMON_RECRUITING_EDIT_EMAIL_INSTRUC"),value:"{/passwordHelpEmail}",submit:[a.submitUsernameHelp,a]})]})]})).addStyleClass("globalLoginFormInputs").addStyleClass("loginFormInputs")]}),(new sap.m.Bar({contentRight:[(new sap.m.Button({type:sap.m.ButtonType.Default,text:b.getText("COMMON_Cancel"),
press:[a.cancelPasswordHelp,a]})).addStyleClass("halfButton").addStyleClass("startButton"),(new sap.m.Button({type:sap.m.ButtonType.Emphasized,text:b.getText("COMMON_RESET"),press:[a.submitPasswordHelp,a]})).addStyleClass("halfButton").addStyleClass("endButton")]})).addStyleClass("loginFormButtons"),(new sap.m.Button({visible:{parts:[{path:"/validCompany"}],formatter:function(a){return!a}},type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",tooltip:b.getText("COMMON_LOGIN_COMPANY_ID_HELP_BTN"),
press:[a.showCompanyIdHelp,a]})).addStyleClass("helpButtonAlign"),(new sap.m.Button({type:sap.m.ButtonType.Transparent,icon:"sap-icon://sys-help",tooltip:b.getText("COMMON_LOGIN_USERNAME_HELP_BTN"),press:[a.showUsernameHelp,a]})).addStyleClass("helpButtonAlign")]}),(new sap.m.Bar({visible:{parts:[{path:"/disableForgetPassword"}],formatter:function(a){return!!a}},contentRight:[(new sap.m.Button({type:sap.m.ButtonType.Default,text:b.getText("COMMON_Ok"),tooltip:b.getText("COMMON_Ok"),press:[a.cancelPasswordHelp,
a]})).addStyleClass("halfButton")]})).addStyleClass("loginFormButtons")]})).addStyleClass("mainAppContent")]})},getControllerName:function(){return"sap.sf.login.bizx.BizXLogin"}})})(jQuery);
(function(a){function g(f){var d=/^([^\?]+)(\?.*|)$/.exec(f||"");if(d){var r={},e=d[1],d=d[2];1<d.length&&a.each(d.substring(1).split("\x26"),function(f,d){var b=d.split("\x3d"),e=b[0],b=b[1],h=r[e];h?a.isArray(h)?h.push(b):h=[h,b]:h=b;r[e]=h});e={pageId:e,params:r};if(0<=b.indexOf(e.pageId))return a.sap.log.info("Hash was parsed and validated properly: ",f),e;a.sap.log.warning("Hash was not validated properly: ",f)}else a.sap.log.warning("Hash did not parse correctly: ",f)}a.sap.declare("sap/sf/surj/bizXLogin/BizXLogin.controller.js");
a.sap.require("sap.sf.surj.shell.core.BizXResourceBundle");var c=sap.ui.getCore(),l,m=function(){return l=l||c.getLibraryResourceBundle("sap.sf.login.i18n")},b="companyCheck companyEntry login usernameHelp passwordHelp companyHelp helpMessage".split(" "),k=a.Deferred(),n={login:!0,usernamehelp:!0,passwordhelp:!0},p={};(function(a){a&&(p[a.pageId]=a.parameters)})(window.bizXLoginStartup);a.sap.require("sap.ui.core.routing.HashChanger");var u=function(f){var d=f.getProperty("/company")||"???",b=f.getProperty("/companyTheme"),
e=a(".globalLoginLogoURL"),e="hidden"!=e.css("visibility")&&e.css("background-image")||null,h,c,g,q=m();e&&(h=e.toLowerCase(),"none"==h?e="":(c=h.indexOf("url("),g=h.length-1,0==c?(c=4,")"==h.charAt(g)&&g--):c=0,'"'==h.charAt(c)&&c++,'"'==h.charAt(g)&&g--,"'"==h.charAt(c)&&c++,"'"==h.charAt(g)&&g--,e=c<=g?e.substring(c,g+1):null));b&&b.logo?(""==e&&(e=b.logo),b=b.logoAlt):(""==e&&(e=IMAGES["/ui/login/img/logos/SF_Logo_Lg-White.png"],k.resolve(!!e)),b=q.getText("COMMON_SF_TITLE"));b&&(h=q.getText("COMMON_LOGO_ALT",
[b||d]))&&(b=h);b||(b=d);f.setProperty("/customLogoAlt",b);f.setProperty("/customLogoTooltip",b);f.setProperty("/customLogoURL",e||"")},w=function(f){var b=f.getProperty("/username");b||(this._bUsedUsername||(b=surj.Util.findURLParam("username"),this._bUsedUsername=!0),b||(b=a(".username .sapMInputBaseInner").val()),b&&f.setProperty("/username",b));b=f.getProperty("/password");b||(b=a(".password .sapMInputBaseInner").val())&&f.setProperty("/password",b)};sap.ui.controller("sap.sf.login.bizx.BizXLogin",
{navigateTo:function(b,d){if(!this.checkActionPrevention()){this._showBusy(!1);var f=this._createHashFromStateObject({pageId:b,params:d});p[f]=a.extend(p[f]||{},d);this.oHashChanger.getHash()!=f?this.oHashChanger.setHash(f):this._processPageParams()}},adjustHeight:function(){var b=a(window).height()-a(".dynamic_header").outerHeight(!0)-a(".bizXFooter").outerHeight(!0);c.byId("bizXLoginApp").setProperty("height",b+"px",!0);jQuery("#bizXLoginApp").css("height",b+"px");this._alignHelpButtons()},goToMainContent:function(){},
showVersionInfo:function(b){var f=m();if(!this._versionDialog){var c=this;this._versionDialog=new sap.m.Dialog({contentWidth:"522px",title:f.getText("COMMON_SF_ABOUT_BOX_TITLE"),content:new sap.ui.core.HTML({content:'\x3cdiv class\x3d"versionInfo"\x3e'+a("#versionInfo").html()+"\x3c/div\x3e"}),endButton:new sap.m.Button({text:f.getText("COMMON_Close"),press:function(){c._versionDialog.close()}}),afterOpen:function(){this.focus()},afterClose:function(){b.focus()}})}this._versionDialog.open()},showQRCode:function(){var a=
m();if(!this.checkActionPrevention()){if(!this._qrDialog){this._qrImage=(new sap.m.Image({densityAware:!1})).addStyleClass("mobileQRCode");var b=this._qrDialog=(new sap.m.Dialog({models:this.oModel,customHeader:new sap.m.Bar({contentMiddle:new sap.m.Text({text:a.getText("COMMON_MOBILE_APP_ACTIVATION")}),contentRight:new sap.m.Button({icon:"sap-icon://decline",tooltip:a.getText("COMMON_Close"),press:function(){b.close()}})}),title:a.getText("COMMON_MOBILE_APP_ACTIVATION"),content:[new sap.ui.layout.HorizontalLayout({visible:{path:"/disableQRCode",
formatter:function(a){return!a}},content:[this._qrImage,new sap.ui.core.HTML({content:'\x3cdiv class\x3d"mobileAppActivationInstructions"\x3e'+a.getText("COMMON_QRCODE_ACTIVATION_INSTRUCTION")+"\x3c/div\x3e"})]}),(new sap.m.Label({visible:"{/disableQRCode}",text:a.getText("COMMON_MOBILE_APP_ACTIVATION_DISABLED")})).addStyleClass("loginQRCodeDisabledMsg")]})).addStyleClass("loginQRCodeDialog")}this._qrImage.setSrc(this.getQRCodeImageUrl());this._qrDialog.open()}},getQRCodeImageUrl:function(){return"/loginqrcode/mobileActivation/generate?companyId\x3d"+
encodeURIComponent(this.oModel.getProperty("/company"))},_initializeHashChanger:function(){if(!this.oHashChanger){this.oHashChanger=sap.ui.core.routing.HashChanger.getInstance();this.oHashChanger.init();this.oHashChanger.attachEvent("hashChanged",a.proxy(this.onHashChanged,this));var b=this.oHashChanger.getHash(),d=a.sap.getObject("bizXLoginStartup.pageId")||"companyEntry";b!=d?this.oHashChanger.setHash(d):this._resolveHash(d)}},onHashChanged:function(a){this._resolveHash(a.getParameter("newHash"))},
showCompanyIdHelp:function(){this.navigateTo("companyHelp")},hideCompanyIdHelp:function(){this.back()},back:function(){this.checkActionPrevention()||window.history.back()},cancelLogin:function(){this.checkActionPrevention()||(this.oModel.setProperty("/company",""),this.oModel.setProperty("/validCompany",null),this.navigateTo("companyEntry"))},backToLogin:function(){this._hasValidCompany()?this._post({ajax:!0,action:"login",parameters:{company:this.oModel.getProperty("/validCompany"),loginMethod:"PWD"}}):
this.navigateTo("companyEntry")},submitCompanyEntry:function(){var a=this.oModel.getProperty("/company");a&&(this._updateCompanyEntryMessage(""),this._post({ajax:!0,action:"login",parameters:{company:a,loginMethod:"PWD"}}))},submitUserLogin:function(){c.byId("loginForm").$().submit()},onLoginFormSubmit:function(a){this._showBusy(!0);return!0},showUsernameHelp:function(){this._post({ajax:!0,action:"usernamehelp",parameters:this.getProperties(["company","username"])})},cancelUsernameHelp:function(){this.backToLogin()},
submitUsernameHelp:function(){if(!this.oModel.getProperty("/disableForgetUsername")){var a=this.oModel.getProperty("/company"),b=this.oModel.getProperty("/usernameHelpEmail");a&&b&&this._post({action:"usernamehelp",parameters:{company:a,useremail:b}})}},showPasswordHelp:function(){this._post({ajax:!0,action:"passwordhelp",parameters:this.getProperties(["company"])})},getProperties:function(b){var f={},c=this;a.each(b,function(a,b){var d=c.oModel.getProperty("/"+b);d&&(f[b]=d)});return f},cancelPasswordHelp:function(){this.backToLogin()},
submitPasswordHelp:function(){if(!this.oModel.getProperty("/disableForgetPassword")){var a=this.oModel.getProperty("/company"),b=this.oModel.getProperty("/passwordHelpUsername"),c={company:a,username:b};this.oModel.getProperty("/retByEmail")&&(c.useremail=this.oModel.getProperty("/passwordHelpEmail"));a&&(b||c.useremail)&&this._post({action:"passwordhelp",parameters:c})}},onAfterRendering:function(){this._handleHiddenLoginForm();var b=this.oModel.getProperty("/currentState");b&&(b=b.pageId,this._processPageParams(),
a.sap.delayedCall(500,this,this._onPageShown,[b]));a(".dynamic_header img").load(a.proxy(this.adjustHeight,this));this.adjustHeight();a(window).resize(a.proxy(this.adjustHeight,this))},afterNavigate:function(a){this._onPageShown(a.mParameters.toId)},_onPageShown:function(a){this._alignHelpButtons(a);this._setAccessKey1(a)},_handleHiddenLoginForm:function(){var b=a("#hiddenLoginForm");if(0<b.length){var d=a("#hiddenUsername").val(),c=a("#hiddenPassword").val();d&&c&&(this.oModel.setProperty("/username",
d),this.oModel.setProperty("/password",c));b.remove()}},_setAccessKey1:function(b){a("#"+b+" .sapMInput input").each(function(b){0==b?a(this).attr("accesskey",1):a(this).removeAttr("accesskey")})},onLogoImageLoad:function(){this._alignHelpButtons();k.done(function(b){a(".globalLoginLogoImg")[b?"addClass":"removeClass"]("globalLoginLogoImgSysFallback")})},onLogoImageError:function(){this._alignHelpButtons()},_alignHelpButtons:function(b){null!=this._alignInterval&&(clearInterval(this._alignInterval),
this._alignInterval=null);if(!b){var d=this.oModel.getProperty("/currentState");b=d&&d.pageId}if(b){var f=function(){var f=a("#"+b+" .helpButtonAlign"),d=a("#"+b+" .inputWithHelp");if(0<d.length&&d.length==f.length){var e=f.parents().filter(function(){var b=a(this).css("position");return"relative"==b||"absolute"==b}).offset().top;f.each(function(b){b=a(d[b]).offset().top-e;a(this).css("top",b+"px")});f.hasClass("hasAligned")||f.addClass("hasAligned")}},e=function(){var f=a("#"+b);return 0<f.length&&
0<f[0].offsetWidth};e()?f():this._alignInterval=setInterval(a.proxy(function(){e()&&(clearInterval(this._alignInterval),f())},this),500)}return!1},_resolveHash:function(a){var b=this.oModel.getProperty("/currentState"),f=g(a);a=c.byId("bizXLoginApp");if(f){this.oModel.setProperty("/currentState",f);var e=b&&b.pageId,f=f.pageId;b||this.oModel.setProperty("/initialPage",f);a&&(e!==f&&a.to(f,this.getTransitionName(e,f)),this._processPageParams())}},getTransitionName:function(a,b){return"companyCheck"==
a?"show":"slide"},_hasValidCompany:function(){return!!this.oModel.getProperty("/validCompany")},_updateCompanyEntryMessage:function(b){this.oModel.setProperty("/companyEntryMessage",b?'\x3cdiv class\x3d"companyEntryMessage" role\x3d"alert"\x3e'+b+"\x3c/div\x3e":"");a.sap.delayedCall(500,this,function(){this._alignHelpButtons()})},_processPageParams:function(){var b=this,d=this.oModel.getProperty("/currentState"),c=this._createHashFromStateObject(d),e=d.pageId,h=p[c]||d.params||{};this._iPageTransitions=
(this._iPageTransitions||0)+1;1==this._iPageTransitions&&a(".skipnav").focus();var g=h.companyTheme;this.oModel.setProperty("/companyTheme",g);"companyEntry"==e&&this.oModel.setProperty("/validCompany",null);var d=a("head"),c=a("body"),k=d.find("#globalLoginCss"),q=h.company;if(q){this.oModel.setProperty("/company",q);this.oModel.setProperty("/validCompany",q);g=g&&g.id||"*";g="/public/theme-api/css/"+encodeURIComponent(q)+"/"+encodeURIComponent(g)+"/ui/login/css/bizx/BizXLoginTheme.dcss";if(0<k.length)k.attr("data-bizx-company")!=
q&&(k.attr("href",g),k.attr("data-bizx-company",q));else{var l=function(){"1px"==t.css("left")||5E3<=(new Date).getTime()-x?m():(n||(a("#sfLoadBlockerLayer").show(),n=!0),v=setTimeout(l,250))},m=function(){t.remove();a("#sfLoadBlockerLayer").hide();var f=b.oModel.getProperty("/currentState");if(f){var d=f.pageId;d&&a.sap.delayedCall(1,b,function(){u.call(this,this.oModel);this._alignHelpButtons(d);a.sap.delayedCall(500,this,this._alignHelpButtons,[d])})}},n=!1,x=(new Date).getTime(),t=a('\x3cdiv class\x3d"themeInfo"\x3e\x3c/div\x3e').css({width:0,
height:0,visibility:"invisible"}).appendTo("body"),k=a("\x3clink\x3e").attr({href:g,type:"text/css",rel:"stylesheet",id:"globalLoginCss"}).on("load",function(){clearTimeout(v);"1px"==t.css("left")?m():setTimeout(m,250)});k.attr("data-bizx-company",q);k.appendTo(d);var v=setTimeout(l,200)}c.addClass("globalThemed")}else this.oModel.getProperty("/validCompany")||(c.removeClass("globalThemed"),k.remove());u.call(this,this.oModel);w.call(this,this.oModel);a.each(["disableForgetPassword","disableForgetUsername",
"companyIdLocked","retByEmail"],function(a,f){var d=h[f];"undefined"!=typeof d&&b.oModel.setProperty("/"+f,!0===d||"true"===d)});switch(e){case "helpMessage":h.companyExpired&&this._showCompanyExpired(h.companyExpired);d=e="";h&&(h.loginMessage&&(e=h.loginMessage),h.loginTips&&(d=h.loginTips));e&&d?this.oModel.setProperty("/helpMessage",'\x3cdiv class\x3d"loginMessage"\x3e\x3cp\x3e'+e+"\x3c/p\x3e\x3cp\x3e"+d+"\x3c/p\x3e\x3c/div\x3e"):e||d?this.oModel.setProperty("/helpMessage",'\x3cdiv class\x3d"loginMessage loginMessageTips"\x3e'+
(e||d)+"\x3c/div\x3e"):this.oModel.setProperty("/helpMessage","\x3cspan\x3e\x3c/span\x3e");break;case "login":this.oModel.setProperty("/disableQRCode",!!h.disableQRCode);h.companyExpired&&this._showCompanyExpired(h.companyExpired);if(!this._hasValidCompany()){this.navigateTo("companyEntry");break}h.companyExpired&&this._showCompanyExpired(h.companyExpired);e=h&&h.loginMessage||"";e||(e=(e=this.oModel.getProperty("/parameters"))&&e.loginMessage||"");this.oModel.setProperty("/loginMessage",e?'\x3cdiv class\x3d"loginMessage"\x3e'+
e+"\x3c/div\x3e":"");break;case "companyEntry":d=(e=h)&&e.companyEntryMessage||"";d||(d=(e=this.oModel.getProperty("/parameters"))&&e.companyEntryMessage||"");d&&(e.companyEntryMessage="");this._updateCompanyEntryMessage(d);break;case "companyCheck":this.oModel.setProperty("/company",h.checkCompany),this.submitCompanyEntry()}},_showCompanyExpired:function(a){a=a.messages;var b=(new sap.m.Dialog({title:a.COMPANY_EXPIRE_MSG,content:new sap.m.Text({text:a.COMPANY_EXPIRE_MSG2}),endButton:new sap.m.Button({text:a.Close,
tooltip:a.Close,press:function(){b.close()}}),afterClose:function(){b.destroy()}})).open()},getInitialPage:function(){return this.getModel().getProperty("/initialPage")},getModel:function(){this.oModel||(this.oModel=new sap.ui.model.json.JSONModel({company:"",username:"",password:"",passwordHelpUsername:"",usernameHelpEmail:"",loginMessage:"\x3cspan\x3e\x3c/span\x3e",parameters:a.sap.getObject("bizXLoginStartup.parameters")}),this.getView().setModel(this.getModel()));this._initializeHashChanger();
return this.oModel},_createHashFromStateObject:function(b){var d={},f=this,e=!1;a.each(b.params||{},function(a,c){f._isHashableParameter(b,a,c)&&(e=!0,d[a]=c)});return b.pageId+(e?"?"+a.param(d):"")},_isHashableParameter:function(a,b,c){return!1},_showBusy:function(a){this.oModel.setProperty("/busy",a);if(a){var b=this;this._busyTimeout=setTimeout(function(){b._busyDialog||(b._busyDialog=new sap.m.BusyDialog);b._busyDialog.open();b._busyTimeout=null},100)}else null!=this._busyTimeout&&(clearTimeout(this._busyTimeout),
this._busyTimeout=null),this._busyDialog&&this._busyDialog.close()},_post:function(b){if(!this.checkActionPrevention()){var d=b.action,c="/"+d;0<=window.location.href.indexOf("/surj/")&&(c="/surj"+c+"/");"string"==typeof window.ajaxSecKey&&(c+="?_s.crb\x3d"+window.ajaxSecKey);var e=b.parameters||{},f=this.oModel.getProperty("/parameters/hiddenInputs"),e=a.extend({},e,f);if(b.ajax&&this.isAjaxPostSupported(d,e)){var g=this;e.ajax=1;a.ajax({type:"POST",url:c,data:e,error:function(){b.ajax=!1;g._post(b)},
success:function(a){var b=a.indexOf("/*JSONSTART*/"),c=a.indexOf("/*JSONEND*/");0<=b&&0<=c?(a=eval("("+a.substring(b+13,c)+")"),g.navigateTo(a.pageId,a.parameters)):(document.open(),document.write(a),document.close())}})}else{var k=a("\x3cform\x3e\x3c/form\x3e").attr({action:c,method:"POST"}).css({visibility:"hidden",position:"fixed",top:"100px",left:0}).appendTo("body");a.each(e,function(b,c){a("\x3cinput\x3e").attr({type:"hidden",name:b,value:c}).appendTo(k)});a('\x3cinput type\x3d"submit" /\x3e').appendTo(k);
this._showBusy(!0);k[0].submit()}}},isAjaxPostSupported:function(a,b){return!!n[a]},checkActionPrevention:function(){var a=m(),b=this.oModel.getProperty("/companyTheme");return b&&b.themePreview?(alert(a.getText("COMMON_LOGIN_THEMING_PREVIEW_DISABLED_ACTION")),!0):!1}})})(jQuery);
(function(a){var g=a.Deferred();a.sap.setObject("sap.sf.login.bizx.BizXLogin",{setController:function(a){g.resolve(a)},navigateTo:function(a,l){g.done(function(c){c.navigateTo(a,l)})},showVersionInfo:function(a){g.done(function(c){c.showVersionInfo(a)})},goToMainContent:function(){g.done(function(a){a.goToMainContent()})},cancelLogin:function(){g.done(function(a){a.cancelLogin()})}});window.enterDifferentCompanyID=function(){var c=a.sap.getObject("sap.sf.login.bizx.BizXLogin");c&&c.cancelLogin();
return!1}})(jQuery);