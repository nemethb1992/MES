<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="phoenix.mes.abas.GenericTask.Status"%>



<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	List<Task> li = (List<Task>)request.getAttribute("StationList");
	AbasConnection abasConnection = (AbasConnection)request.getAttribute("abasConnection");

	BigDecimal summedProductionTime = BigDecimal.ZERO;
	String cssClass;
	int i =0;
	for (Task task: li) {
		final Task.Details taskDetails = task.getDetails(abasConnection);
				boolean progress = (task.getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true : false);
				summedProductionTime = summedProductionTime.add(taskDetails.getCalculatedProductionTime());
				
				switch(taskDetails.getStatus()){
				case IN_PROGRESS:
					cssClass = "dnd-container-inprogress";
					break;
				case SUSPENDED:
					cssClass = "dnd-container-suspended";
					break;
				case INTERRUPTED:
					cssClass = "dnd-container-interrupted";
					break;
				default:
					cssClass = "";
					break;			
				}
				%><li class="dnd-container station-list-item sort-list-holder list-group <%=cssClass%>  col-12 px-0" value="<%=task.getWorkSlipId()%>">
								<input class="d-none workSlipId" value="<%=task.getWorkSlipId()%>">
								<div class="container-fluid ">
									<div class="row ">
										<div class="col-1 drag px-0">
										</div>	
										<div class="col-11 ">
											<div class="row">
												<div class="<%=(progress ? "col-12" : "col-11")%> content-div">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></th>
																<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td><input class='w-100 task-input' readonly value='<%=taskDetails.getWorkSlipNo()%>'/></td>
																<td><input class='w-100 task-input' readonly value='<%=taskDetails.getUsage()%>'/></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="pl-0 <%=(progress ? "d-none" : "col-1")%>">
													<div class='row h-100'>

														<div class='col-12 my-1 px-0'>
															<input
																class='h-100 w-100 task-panel-button mini-button remove-task-button'
																onclick='RemoveFromStation(this)' type='button'>
														</div>
													</div>
												</div>
											</div>
											<div
												class="container-fluid collapse list-item-extended-content"
												id="list-item-extended-content-station-<%=i%>">
												<div class="row">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th scope="col"><%=of.getWord(DictionaryEntry.GET_FINISHED)%></th>
																<th scope="col"><%=of.getWord(DictionaryEntry.ARTICLE)%></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td><input class='w-100 task-input' readonly value='<%=of.formatDate(taskDetails.getFinishDate())%>'/></td>
																<td><input class='w-100 task-input' readonly value='<%=taskDetails.getProductIdNo()%>'/></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="row">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th scope="col"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></th>
																<th scope="col"><%=of.getWord(DictionaryEntry.NAME)%></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td><input class='w-100 task-input' readonly value='<%=taskDetails.getProductSwd()%>'/></td>
																<td><input class='w-100 task-input' readonly value='<%=taskDetails.getProductDescription()%>'/></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="row">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th scope="col"><%=of.getWord(DictionaryEntry.CALCULATED_PROD_TIME)%></th>
																<th scope="col"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td><input class='w-100 task-input' readonly value='<%=of.formatTime(taskDetails.getCalculatedProductionTime())%>'/></td>
																<td><input class='w-100 task-input' readonly value='<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity()) +" "+ taskDetails.getStockUnit()%>'/></td>
															</tr>
														</tbody>
													</table>
												</div>
																	<div class='row'>
						<div class='col-12'>
							<button type="button" onclick='openDataSheetModal(this)'
								value='<%=task.getWorkSlipId()%>'
								class="btn btn-info w-100 my-2"><%=of.getWord(DictionaryEntry.OPEN_TASK_DATASHEET)%></button>
						</div>
					</div>
											</div>
										</div>
									</div>
									<div class='row h-25'>
										<div class="col button-holder-list">
											<div class="row dnd-input-div">
												<input class="w-100 more-button-station" onclick=""
													type="button" data-toggle="collapse"
													data-target="#list-item-extended-content-station-<%=i%>"
													aria-expanded="false"
													aria-controls="list-item-extended-content-station-<%=i%>">
											</div>
										</div>
									</div>
								</div>
							</li>
<%i++;}
request.setAttribute("summedProductionTime", summedProductionTime);
%>