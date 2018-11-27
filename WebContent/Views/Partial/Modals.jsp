<div class="modal fade" id="interrupt-level1" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Biztosan megakarja szakítani?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Bezárás</button>
        <button type="button" class="btn btn-primary" onclick='InterruptTask()'>Megszakítás</button>
      </div>
    </div>
  </div>
  <form method='Post' action='${pageContext.request.contextPath}/OpenTask' class='d-none interrupt-form'></form>
</div>