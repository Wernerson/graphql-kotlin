(window.webpackJsonp=window.webpackJsonp||[]).push([[63],{131:function(e,r,t){"use strict";t.r(r),t.d(r,"frontMatter",(function(){return i})),t.d(r,"metadata",(function(){return l})),t.d(r,"toc",(function(){return s})),t.d(r,"default",(function(){return p}));var n=t(3),a=t(7),o=(t(0),t(181)),i={id:"release-proc",title:"Releasing a new version",original_id:"release-proc"},l={unversionedId:"contributors/release-proc",id:"version-3.x.x/contributors/release-proc",isDocsHomePage:!1,title:"Releasing a new version",description:"In order to release a new version we need to draft a new release",source:"@site/versioned_docs/version-3.x.x/contributors/release-proc.md",slug:"/contributors/release-proc",permalink:"/graphql-kotlin/docs/3.x.x/contributors/release-proc",editUrl:"https://github.com/ExpediaGroup/graphql-kotlin/tree/master/website/versioned_docs/version-3.x.x/contributors/release-proc.md",version:"3.x.x",lastUpdatedBy:"Shane Myrick",lastUpdatedAt:1617827463,sidebar:"version-3.x.x/docs",previous:{title:"Maven Plugin",permalink:"/graphql-kotlin/docs/3.x.x/plugins/maven-plugin"}},s=[{value:"Release requirements",id:"release-requirements",children:[]}],c={toc:s};function p(e){var r=e.components,t=Object(a.a)(e,["components"]);return Object(o.b)("wrapper",Object(n.a)({},c,t,{components:r,mdxType:"MDXLayout"}),Object(o.b)("p",null,"In order to ",Object(o.b)("a",{parentName:"p",href:"https://github.com/ExpediaDotCom/graphql-kotlin/releases"},"release a new version")," we need to draft a new release\nand tag the commit. Releases are following ",Object(o.b)("a",{parentName:"p",href:"https://semver.org/"},"semantic versioning")," and specify major, minor and patch version."),Object(o.b)("p",null,"Once release is published it will trigger corresponding ",Object(o.b)("a",{parentName:"p",href:"https://github.com/ExpediaGroup/graphql-kotlin/blob/master/.github/workflows/release.yml"},"Github Action"),"\nbased on the published release event. Release workflow will then proceed to build and publish all library artifacts to ",Object(o.b)("a",{parentName:"p",href:"https://central.sonatype.org/"},"Maven Central"),"."),Object(o.b)("h3",{id:"release-requirements"},"Release requirements"),Object(o.b)("ul",null,Object(o.b)("li",{parentName:"ul"},"tag should specify newly released library version that is following ",Object(o.b)("a",{parentName:"li",href:"https://semver.org/"},"semantic versioning")),Object(o.b)("li",{parentName:"ul"},"tag and release name should match"),Object(o.b)("li",{parentName:"ul"},"release should contain the information about all the change sets that were included in the given release. We are using ",Object(o.b)("inlineCode",{parentName:"li"},"release-drafter")," to help automatically\ncollect this information and generate automatic release notes.")))}p.isMDXComponent=!0},181:function(e,r,t){"use strict";t.d(r,"a",(function(){return u})),t.d(r,"b",(function(){return m}));var n=t(0),a=t.n(n);function o(e,r,t){return r in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}function i(e,r){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);r&&(n=n.filter((function(r){return Object.getOwnPropertyDescriptor(e,r).enumerable}))),t.push.apply(t,n)}return t}function l(e){for(var r=1;r<arguments.length;r++){var t=null!=arguments[r]?arguments[r]:{};r%2?i(Object(t),!0).forEach((function(r){o(e,r,t[r])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):i(Object(t)).forEach((function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(t,r))}))}return e}function s(e,r){if(null==e)return{};var t,n,a=function(e,r){if(null==e)return{};var t,n,a={},o=Object.keys(e);for(n=0;n<o.length;n++)t=o[n],r.indexOf(t)>=0||(a[t]=e[t]);return a}(e,r);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)t=o[n],r.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(a[t]=e[t])}return a}var c=a.a.createContext({}),p=function(e){var r=a.a.useContext(c),t=r;return e&&(t="function"==typeof e?e(r):l(l({},r),e)),t},u=function(e){var r=p(e.components);return a.a.createElement(c.Provider,{value:r},e.children)},b={inlineCode:"code",wrapper:function(e){var r=e.children;return a.a.createElement(a.a.Fragment,{},r)}},d=a.a.forwardRef((function(e,r){var t=e.components,n=e.mdxType,o=e.originalType,i=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),u=p(t),d=n,m=u["".concat(i,".").concat(d)]||u[d]||b[d]||o;return t?a.a.createElement(m,l(l({ref:r},c),{},{components:t})):a.a.createElement(m,l({ref:r},c))}));function m(e,r){var t=arguments,n=r&&r.mdxType;if("string"==typeof e||n){var o=t.length,i=new Array(o);i[0]=d;var l={};for(var s in r)hasOwnProperty.call(r,s)&&(l[s]=r[s]);l.originalType=e,l.mdxType="string"==typeof e?e:n,i[1]=l;for(var c=2;c<o;c++)i[c]=t[c];return a.a.createElement.apply(null,i)}return a.a.createElement.apply(null,t)}d.displayName="MDXCreateElement"}}]);