

<div class='TM_backBtn btnstyle selectBackBtn col-12 px-0'>
	<img
	class='h-100'
		src='${pageContext.request.contextPath}/Public/icons/backArrow.svg' />
	<p>Menu</p>
</div>
<div class=' col-2 pr-1'>
	<div class='cont_header'>
		<div class='input-icon-holderDiv'>
<!-- 			<img class='icon-form search-icon-form WSSearchImg' -->
<%-- 				src='${pageContext.request.contextPath}/Public/icons/searchSignWhite.svg'><input --%>
<!-- 				class='ts_searchInp WSSearchIn' value=''> -->
			<div class='refresh_btn'></div>
		</div>
	</div>
	<div class='tmts_stationContainer row px-3'>
	</div>
</div>


<div class='sortContDiv col-10 pr-3 pl-0'>

	<div class='cont_header'>
		<div class='input-icon-holderDiv ts-loggedName-div'>
			<img class='icon-form'
				src='${pageContext.request.contextPath}/Public/icons/user-shape-white.svg'><input
				disabled value=''>
		</div>
	</div>
	<div class='sortStationListCont container'>
		<div class='sortContDiv_nav row px-0'>
			<div class='abas-list-head sort-list-head sort-list col-6'>
				<div class='row'>
					<div class=' px-0  col-12'>
						<div class='container'>
							<div class='row'>
								<div class='col-8'>
									<input class='datepicker_own w-100 h-100' type='date' />
								</div>
								<div class='col-2 px-0'>
									<button class='date-button date-refresh h-100 w-100'></button>
								</div>
								<div class='col-2 px-0'>
									<button class='date-button date-null h-100 w-100'></button>
								</div>
							</div>
						</div>
					</div>
					<div class='input-icon-holderDiv  col px-0'>
<!-- 						<img class='icon-form search-icon-form AbasSearchImg' -->
<%-- 							src='${pageContext.request.contextPath}/Public/icons/searchSign.svg'><input --%>
<!-- 							class='ts_searchInp AbasSearchIn' value=''> -->
					</div>
				</div>
			</div>

			<div class='ws-list-head sort-list-head sort-list col-6'>
				<div class='input-icon-holderDiv stationName-holderDiv'>
					<img class='icon-form'
						src='${pageContext.request.contextPath}/Public/icons/computerSignGray.svg'><input
						class='ts_wsNameInp station_label' value='Válasszon állomást'>
				</div>
				<div class='input-icon-holderDiv sum-holderDiv'>
					<img class='icon-form'
						src='${pageContext.request.contextPath}/Public/icons/sumSignGray.svg'><input
						class='ts_sumTime' value='0:00:00'>
				</div>
			</div>
		</div>

		<div class='sortContDiv_ListHolder row'>
			<div class='abas-list-holder sort-list-holder  col-6 px-0'>
				<div class='dnd-frame dndf1 abas-list h-100 row'>


					<!-- 										<div class='dnd-container col-12 px-0' value='3'> -->
					<!-- 											<div class='container px-0'> -->
					<!-- 												<div class='row w-100 mx-auto'> -->
					<!-- 													<div class='col-5 py-2 dnd-input-div'> -->
					<!-- 														<p>Munkaszám</p> -->
					<!-- 														<input disabled class='dnd-input dnd-in1' value='A22322123'> -->
					<!-- 														<p>Cikkszám</p> -->
					<!-- 														<input disabled class='dnd-input dnd-in1' value='006334417'> -->
					<!-- 														<p>Keresöszó</p> -->
					<!-- 														<input disabled class='dnd-input dnd-in1' value='test-kereso'> -->
					<!-- 													</div> -->
					<!-- 													<div class='col-5 py-2 dnd-input-div'> -->
					<!-- 														<p>Felhasználás</p> -->
					<!-- 														<input disabled class='dnd-input dnd-in1' value='beépülö'> -->
					<!-- 														<p>Termék megnevezés</p> -->
					<!-- 														<input disabled class='dnd-input dnd-in1' value='Terméknév'> -->
					<!-- 														<p>Termék megnevezés 2</p> -->
					<!-- 														<input disabled class='dnd-input dnd-in1' value='Terméknév második'> -->
					<!-- 													</div> -->
					<!-- 													<div class='col-2 dnd-input-div px-0'> -->
					<!-- 														<input class='h-100 w-100 task-panel-button' type='button'> -->
					<!-- 													</div> -->
					<!-- 												</div> -->
					<!-- 											</div> -->
					<!-- 										</div> -->

				</div>
			</div>
			<div class='ws-list-holder sort-list-holder col-6 px-0'>
				<div class='dnd-frame h-100 dndf2'>


					<!-- 					<div class="loaderCycle mx-auto mt-5  task-cycle"></div> -->
					<!-- 					<div class='dnd-container col-12 px-0' value='3'> -->
					<!-- 						<div class='container px-0'> -->
					<!-- 							<div class='row w-100 mx-auto'> -->
					<!-- 								<div class='col-5 py-2 dnd-input-div'> -->
					<!-- 									<p>Munkaszám</p> -->
					<!-- 									<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 									<p>Cikkszám</p> -->
					<!-- 									<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 									<p>Keresöszó</p> -->
					<!-- 									<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 								</div> -->
					<!-- 								<div class='col-5 py-2 dnd-input-div'> -->
					<!-- 									<p>Felhasználás</p> -->
					<!-- 									<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 									<p>Termék megnevezés</p> -->
					<!-- 									<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 									<p>Termék megnevezés 2</p> -->
					<!-- 									<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 								</div> -->
					<!-- 								<div class='col-2 dnd-input-div px-0'> -->
					<!-- 									<div class='row w-100 mx-auto h-100 d-flex'> -->
					<!-- 										<div class='col-12 px-0'> -->
					<!-- 											<input class='h-100 w-100 task-panel-button mini-button up-task-button' type='button'> -->
					<!-- 										</div> -->
					<!-- 										<div class='col-12 my-1 px-0'> -->
					<!-- 											<input class='h-100 w-100 task-panel-button mini-button remove-task-button' type='button'> -->
					<!-- 										</div> -->
					<!-- 										<div class='col-12 px-0'> -->
					<!-- 											<input class='h-100 w-100 task-panel-button mini-button down-task-button' type='button'> -->
					<!-- 										</div> -->
					<!-- 									</div> -->
					<!-- 								</div> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->



				</div>
			</div>
		</div>
	</div>
</div>


