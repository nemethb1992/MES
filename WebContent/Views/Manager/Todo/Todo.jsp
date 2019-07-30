<%@include file="/Views/Header.jsp"%>
<%@page import="java.util.Locale"%>
<script>
	
<%@ include file="/Views/Manager/Todo/Todo.js"%>
	
<%String displayName = (String) session.getAttribute("displayname");
			if (outputFormatter.getLocale() == Locale.GERMAN || outputFormatter.getLocale() == Locale.ENGLISH) {
				try {
					String auxiliaryArr[] = displayName.split(" ");
					String auxiliary = "";

					int i = 0;
					for (String part : auxiliaryArr) {
						if (i > 0) {
							auxiliary += part;
							auxiliary += " ";
						}
						i++;
					}

					auxiliary += auxiliaryArr[0];
					displayName = auxiliary;
				} catch (Exception e) {
				}
			}%>
	
</script>
<div class='container-fluid h-100'>
	<p class='actual-time h5'></p>
	<div class='row manager-top-nav'>
		<div class='col-12 h-100 px-0 topNav'>
			<div class='h-100 w-100 mx-0 row'>
				<div class='h-100 col col-nav-logo float-left'>
					<!-- 					<img -->
					<%-- 						src="${pageContext.request.contextPath}/Public/icons/pm_logo_normal.svg" --%>
					<!-- 						class='d-block m-3 img-fluid LogoMiniPM' /> -->
				</div>
				<div class='language-top-nav h-100 px-0'>
					<%@include file="/Views/Partial/LanguageSelector.jsp"%>
				</div>
				<div
					class='personal-data-display manager-personal-data h-100 float-right px-0'>
					<div class='form-group'>
						<input
							class='form-control h-100 personal-form-control personal-form-name'
							disabled value='<%=displayName%>'>
					</div>
					<div class='form-group personal-secondery'>
						<input
							class='form-control h-100 personal-form-control personal-date'
							disabled>
					</div>
					<div class='form-group personal-secondery'>
						<input
							class='form-control h-100 personal-form-control personal-time'
							disabled>
					</div>
				</div>
				<div class='top-nav-button h-100 px-0 col-nav-back float-right'>
					<form method='POST' class='h-100'
						action='<%=response.encodeURL(request.getContextPath() + "/Main")%>'>
						<input class='btn_back-gray' type='submit' value='' />
					</form>
				</div>
				<div
					class='top-nav-button px-0 d-block col col-nav-logout h-100 float-right'>
					<form method='POST' class='h-100'
						action='<%=response.encodeURL(request.getContextPath() + "/Logout")%>'>
						<input class='btn_logout-gray' type='submit' value='' />
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class='row my-lower-row'>
		<div class=' col-2'>
			<div class='row station-select-row h-100'>
				<div class='cont_header col-12 px-0'>
					<div class='input-icon-holderDiv float-right h-100'>
						<div class='refresh_btn h-100' onclick='refreshButton()'>
							<img class='h-100 float-left p-3'
								src='${pageContext.request.contextPath}/Public/icons/backArrowgray.svg'>
						</div>
					</div>
				</div>
				<div class='col-12 cont_content'>
					<div class='station-container row'></div>
				</div>
			</div>
		</div>
		<div class='sortContDiv h-100 col-10 pr-0'>
			<div class='sortStationListCont h-100 container-fluid'>
				<div class='sortContDiv_nav row px-0'>
					<div class='abas-list-head sort-list-head sort-list h-100 col-5'>
						<div class='row h-100'>
							<div class='py-2 col-12'>
								<div class='row h-100 py-1'>
									<div class='col-10'>
										<input data-date-format="yyyy/mm/dd"
											class='datepicker_own w-100 h-100' id="datepicker">
										<!-- 													<input class='datepicker_own w-100 h-100' type='date' style='font-size: 17px;'/> -->
									</div>
									<div class='col-2 px-0'>
										<button class='date-button date-null h-100 w-100'></button>
									</div>
								</div>
							</div>
							<div class='input-icon-holderDiv  col px-0'></div>
						</div>
					</div>
					<div class='ws-list-head sort-list-head sort-list col-7  h-100'>
						<div class='row h-100 mx-0'>
							<div
								class='input-icon-holderDiv stationName-holderDiv col-8 h-100'>
								<input disabled class='ts_wsNameInp station_label h-100  p-3'
									value='<%=outputFormatter.getWord(DictionaryEntry.SELECT_A_WORKSTATION)%>'>
							</div>
							<div class='input-icon-holderDiv h-100 col-4   sum-holderDiv'>
								<div class='row h-100 '>
									<input disabled
										class='ts_sumTime h-100 disabled col-12 text-right float-right pr-4 py-3'
										value='0:00:00'>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='sortContDiv_ListHolder row'>
					<div class='abas-list-holder h-100   sort-list-holder  col-9 px-0'>
						<div class='abas-list dnd-frame dndf1 m-0 row'></div>
					</div>
					<div class='ws-list-holder h-100 sort-list-holder col-3 px-0'>

						<ul class='dnd-frame h-100 m-0 dndf2 station-list'>
<%for(int i = 0; i < 10; i++){ %>

					<li class="dnd-container station-list-item sort-list-holder list-group col-12 px-0">
								<input class="d-none workSlipId" value="(8257188,9,0)">
								<div class="container-fluid ">
									<div class="row ">
										<div class="col ">
											<div class="row">
												<div class="col-11 drag-surface">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th style="border: transparent" scope="col">Munkalapszám</th>
																<th style="border: transparent" scope="col">Felhasználás</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>218121003</td>
																<td>0471832B_1</td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="pl-2 col-1">
													<div class="row h-100">
														<div class="col-12 px-0">
															<input class="h-100 w-100 task-panel-button mini-button up-task-button" onclick="MoveTaskUp(this)" type="button">
														</div>
														<div class="col-12 my-1 px-0">
															<input class="h-100 w-100 task-panel-button mini-button remove-task-button" onclick="RemoveFromStation(this)" type="button">
														</div>
														<div class="col-12 px-0">
															<input class="h-100 w-100 task-panel-button mini-button down-task-button" onclick="MoveTaskDown(this)" type="button">
														</div>
													</div>
												</div>
											</div>
											<div class="container-fluid collapse list-item-extended-content" id="list-item-extended-content-station-2">
												<div class="row">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th scope="col">Tervezett kezdés</th>
																<th scope="col">Cikkszám</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>2019.03.19.</td>
																<td>00000000016833P</td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="row">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th scope="col">Keresöszó</th>
																<th scope="col">Megnevezés</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>P00126324.2</td>
																<td>BA 281706 F Bocube Alu-Gehäuse-7024 2</td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="row">
													<table class="table station-list-table mb-0">
														<thead>
															<tr>
																<th scope="col">Gyártási idö</th>
																<th scope="col">Nyitott mennyiség</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>0:00:36</td>
																<td>3 darab</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="row h-25">
										<div class="col button-holder-list">
											<div class="row dnd-input-div">
												<input class="w-100 more-button-station" onclick="" type="button" data-toggle="collapse" data-target="#list-item-extended-content-station-2" aria-expanded="false" aria-controls="list-item-extended-content-station-2">
											</div>
										</div>
									</div>
								</div>
							</li>	
<%} %>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>