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
						<div class='refresh_btn h-100'>
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
						<div class='abas-list dnd-frame dndf1 m-0 row'>



							<!-- ######### Teszt elem -->
							<%
								for (int i = 0; i < 20; i++) {
							%>
							<div class="dnd-container abas-list-item  col-12 px-0">
								<input class="d-none workSlipId" value="(8399380,9,0)">
								<div class="container-fluid ">
									<div class="row ">
										<div class="col-11  px-0">
											<div class='row'>
												<div class='col'>
													<table class="table abas-list-table mb-0">
														<thead>
															<tr>
																<th scope="col">Munkalapszám</th>
																<th scope="col">Cikkszám</th>
																<th scope="col">Keresöszó</th>
																<th scope="col">Felhasználás</th>
																<th scope="col">Megnevezés</th>
																<th scope="col">Tervezett kezdés</th>
																<th scope="col">Nyitott mennyiség</th>
																<th scope="col">Gyártási idö</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>258432002</td>
																<td>8730EP010780R</td>
																<td>RK+8730EP010780</td>
																<td>414007RK_1</td>
																<td>F-Rohr Ø30x2 für EP 30</td>
																<td>2019.06.25.</td>
																<td>4 darab</td>
																<td>0:34:48</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class='row'>
												<div class='col-1'>
													<p class='pt-2 pl-2'>
														<b>Indoklás:</b>
													</p>
												</div>
												<div class='col-11'>
													<p class='pt-2'>8730EP010780R</p>
												</div>
											</div>

										</div>
										<div class="col-1 button-holder-list">
											<div class="row h-50 dnd-input-div">
												<input
													class="h-100 w-100 task-panel-button task-panel-button-add"
													onclick="PushToStation(this)" type="button">
											</div>
											<div class=" row h-50 dnd-input-div">
												<input
													class="h-100 w-100 task-panel-button task-panel-button-more"
													onclick="" type="button">
											</div>
										</div>
									</div>

								</div>
							</div>

							<%
								}
							%>
							<!-- 			######		Eddig	 -->

							<!-- ######### Teszt elem -->
							<%-- 							<%for(int i = 0; i < 20; i++){ %> --%>
							<!-- 							<div class="dnd-container abas-list-item  col-12 px-0"> -->
							<!-- 								<input class="d-none workSlipId" value="(8399380,9,0)"> -->
							<!-- 								<div class="container-fluid h-100"> -->
							<!-- 									<div class="row h-100"> -->
							<!-- 										<div class="col abas-listitem-data-col px-0"> -->
							<!-- 											<div class="container-fluid"> -->
							<!-- 												<div class="row"> -->
							<!-- 													<div class="col my-col-1 article-col px-1 pl-2 py-2 dnd-input-div"> -->
							<!-- 														<p>Munkalapszám</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">258432002</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-2 px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Cikkszám</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">8730EP010780R</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-3 px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Keresöszó</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">RK+8730EP010780</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-4 placeofuse-col  px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Felhasználás</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">414007RK_1</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-5 px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Megnevezés</p> -->
							<!-- 														<textarea wrap="soft" class="dnd-input dnd-in1">F-Rohr Ø30x2 für EP 30</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-6 px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Tervezett kezdés</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">2019.06.25.</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-7 px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Nyitott mennyiség</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">4 darab</textarea> -->
							<!-- 													</div> -->
							<!-- 													<div class="col my-col-8 px-1 py-2 dnd-input-div"> -->
							<!-- 														<p>Gyártási idö</p> -->
							<!-- 														<textarea readonly="" class="dnd-input dnd-in1">0:34:48</textarea> -->
							<!-- 													</div> -->
							<!-- 												</div> -->
							<!-- 											</div> -->
							<!-- 										</div> -->
							<!-- 										<div class="col col-button p-1"> -->
							<!-- 											<div class="w-100 h-100 dnd-input-div px-0"> -->
							<!-- 												<input class="h-100 w-100 task-panel-button" onclick="PushToStation(this)" type="button"> -->
							<!-- 											</div> -->
							<!-- 										</div> -->
							<!-- 									</div> -->
							<!-- 								</div> -->
							<!-- 							</div> -->

							<%-- 							<%}%>			 --%>
							<!-- 			######		Eddig	 -->


						</div>
					</div>
					<div class='ws-list-holder h-100 sort-list-holder col-3 px-0'>
						<div class='dnd-frame h-100 m-0 dndf2 station-list'>
							<!-- 						########## Teszt station -->

							<!-- Teszt elem -->
							<%
								for (int i = 0; i < 5; i++) {
							%>
							<div class="dnd-container station-list-item  col-12 px-0">
								<input class="d-none workSlipId" value="(8399380,9,0)">
								<div class="container px-0">
									<div class="row w-100 mx-auto">
										<div class="col-4 pr-0 py-2 dnd-input-div">
											<p>Munkalapszám</p>
											<textarea readonly="" class="dnd-input dnd-in1">258432002</textarea>
											<p>Cikkszám</p>
											<textarea readonly="" class="dnd-input dnd-in1">8730EP010780R</textarea>
											<p>Tervezett kezdés</p>
											<textarea readonly="" class="dnd-input dnd-in1">2019.06.25.</textarea>
											<p>Nyitott mennyiség</p>
											<textarea readonly="" class="dnd-input dnd-in1">4 darab</textarea>
										</div>
										<div class="col-6 pr-3 py-2 dnd-input-div">
											<p>Keresöszó</p>
											<textarea readonly="" class="dnd-input dnd-in1">RK+8730EP010780</textarea>
											<p>Megnevezés</p>
											<textarea readonly="" class="dnd-input dnd-in1">F-Rohr Ø30x2 für EP 30</textarea>
											<p>Gyártási idö</p>
											<textarea readonly="" class="dnd-input dnd-in1">0:34:48</textarea>
										</div>
										<div class="col-2  dnd-input-div px-0">
											<div class="row w-100 mx-auto h-100 d-flex">
												<div class="col-12 px-0">
													<input
														class="h-100 w-100 task-panel-button mini-button up-task-button"
														onclick="MoveTaskUp(this)" type="button">
												</div>
												<div class="col-12 my-1 px-0">
													<input
														class="h-100 w-100 task-panel-button mini-button remove-task-button"
														onclick="RemoveFromStation(this)" type="button">
												</div>
												<div class="col-12 px-0">
													<input
														class="h-100 w-100 task-panel-button mini-button down-task-button"
														onclick="MoveTaskDown(this)" type="button">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<%
								}
							%>
							<!-- 						############## Eddig -->

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>