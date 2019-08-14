<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>

<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	String workSlipId = (String) request.getAttribute("workSlipId");
	OutputFormatter of = (OutputFormatter) request.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details) request.getAttribute("TaskDetails");
%>
<div class="modal fade my-fade" id="DataSheetModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div
		class="modal-dialog modal-lg modal-dialog-centered my-datasheet-modal-dialog"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.ARTICLE)%>:
					<%=taskDetails.getProductIdNo()%></h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body pt-0 " style="background: #f3f3f3;">

				<div class='row task-details-row task-details-manager-row'>
					<div class='left-buttons col-2 col-md-2 col-lg-2 col-xl-2'>
						<div class='row task-details-manager-row'>
							<div class='col light-shadow bord-radius-nav'>
								<div class='btn_leftNavigation big-nav-button-modal row'>
									<button class='col-12 px-0 navigation-button'
										id='navigation-button-1' onclick='ModalDataSheetTabButtonClic(this)' 
										value='<%=workSlipId%>'>
										<p class='nav-btn-1 nav-label nav-label-modal'><%=of.getWord(DictionaryEntry.GENERAL)%></p>
									</button>
								</div>
								<div class='btn_leftNavigation big-nav-button-modal row'>
									<button class='col-12 px-0 navigation-button'
										id='navigation-button-7' onclick='ModalDataSheetTabButtonClic(this)' 
										value='<%=workSlipId%>'>
										<p class='nav-btn-7 nav-label nav-label-modal'><%=of.getWord(DictionaryEntry.TECHNICAL_MANUAL)%></p>
									</button>
								</div>
								<div class='btn_leftNavigation big-nav-button-modal row'>
									<button class='col-12 px-0 navigation-button'
										id='navigation-button-2' onclick='ModalDataSheetTabButtonClic(this)' 
										value='<%=workSlipId%>'>
										<p class='nav-btn-2 nav-label nav-label-modal'><%=of.getWord(DictionaryEntry.DOCUMENTS)%></p>
									</button>
								</div>
								<div class='btn_leftNavigation big-nav-button-modal row'>
									<button class='col-12 px-0 navigation-button'
										id='navigation-button-3' onclick='ModalDataSheetTabButtonClic(this)' 
										value='<%=workSlipId%>'>
										<p class='nav-btn-3 nav-label nav-label-modal'><%=of.getWord(DictionaryEntry.BILL_OF_MATERIAL)%></p>
									</button>
								</div>
								<div class='btn_leftNavigation  big-nav-button-modal row'>
									<button class='col-12 px-0 navigation-button'
										id='navigation-button-4' onclick='ModalDataSheetTabButtonClic(this)' 
										value='<%=workSlipId%>'>
										<p class='nav-btn-4 nav-label nav-label-modal'><%=of.getWord(DictionaryEntry.ORDER_INFO)%></p>
									</button>
								</div>
								<div
									class='btn_leftNavigation bord-radius-nav big-nav-button-modal row'>
									<button class='col-12 px-0 navigation-button'
										id='navigation-button-5' onclick='ModalDataSheetTabButtonClic(this)'
										value='<%=workSlipId%>'>
										<p class='nav-btn-5 nav-label nav-label-modal'><%=of.getWord(DictionaryEntry.FOLLOWING_OPERATIONS)%></p>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div
						class='operator-switch-grid col-10 col-md-10 col-lg-10 col-xl-10 px-0'>
						<div id='ModalSwitchPanel'
							class='rightCont container-fluid task-details-manager-row'>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>