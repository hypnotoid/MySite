<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css"/>
    <meta charset="UTF-16">
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/" method="get">
    <input type="submit" value="Назад"/>
</form>
<c:if test="${cart.size()==0}">
    Здессь пока что ничего нет

</c:if>
<c:if test="${cart.size()>0}">
    Корзина:
    <table>
        <tr>
            <th>Название</th>
            <th>Цена</th>
            <th>Количество</th>
            <th>Цена за все</th>
            <th>Удалить</th>
        </tr>
        <c:forEach items="${cart}" var="product">
            <tr>
                <td>${product.product_name}</td>
                <td>${product.product_price}</td>
                <td>
                    <form action="/cartEdit" method="post">
                        <input type="number" name="amount" pattern="[0-9],{1,}" value="${product.amount}"
                               max="${product.productAmount}"/>
                        <input type="submit" value="Изменить">
                        <input type="hidden" value="${product.product_id}" name="id">
                    </form>
                    <c:if test="${error != null}">${error}</c:if>
                </td>
                <td>${product.total_price}</td>
                <td>
                    <form action="/cartDelete" method="post">
                        <input type="submit" value="Удалить">
                        <input type="hidden" value="${product.product_id}" name="id">
                    </form>
                </td>
            </tr>

        </c:forEach>

    </table>
    Итого: ${total_price}
    <form action="/buy" method="post">
        <input type="submit" value="Купить"/>
    </form>
</c:if>
</body>
</html>
