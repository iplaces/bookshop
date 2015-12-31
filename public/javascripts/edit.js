/**
 * Created by lixin on 2015/12/25.
 */

//function demo(){
//    var textNode = document.createTextNode("您好，欢迎来到北邮书店！");
//    //获取div对象
//    var divNode = document.getElementsByClassName("tbar_lft");
//    //给div添加文本元素
//    divNode.appendChild(textNode);
//}
//您好，欢迎来到北邮书店！<a href="@routes.LogIn.form">请登录</a> | <a href="@routes.SignUp.form">免费注册</a>

var nameCheck = document.getElementById("name-check").innerHTML ;
if(nameCheck == ""){
    var login = addHref("/login", "请登录", "sss");
    document.getElementById("sss").innerHTML += " | ";
    var register = addHref("/signup", "免费注册", "sss");
}   else {
    var user = addHref("/", nameCheck, "sss");
    document.getElementById("sss").innerHTML += " | ";
    var logout = addHref("/logout", "退出", "sss");
}




function addHref(link, text, id) {
    var aNode = document.createElement("a");
    aNode.href = link;
    //aNode.setAttribute("href","http://sh.itcast.cn");
    aNode.innerHTML = text;
    //获取div对象
    var divNode = document.getElementById(id);
    //给div添加文本元素
    divNode.appendChild(aNode);

}


