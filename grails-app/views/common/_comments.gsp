<g:if test="${!feedbacks.empty}">
    <table>
        <tr>
            <th>Comment</th>
            <th style="white-space: nowrap;">Date of Visit</th>
        </tr>
        <g:each in="${feedbacks}" status="i" var="feedback">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${feedback.npsReason}</td>
                <td style="white-space: nowrap;"><g:formatDate date="${feedback.dateOfVisit}" format="dd MMM yyyy"/></td>
            </tr>
        </g:each>
    </table>

    <div class="pagination">
        <g:paginate total="${totalCount}" id="${id}"/>
    </div>
</g:if>