<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:redirect url="/admin/home"/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_USER') or isAnonymous() or hasRole('ROLE_USER_FACEBOOK') or hasRole('ROLE_USER_GOOGLE')">
    <c:redirect url="/home"/>
</sec:authorize>
