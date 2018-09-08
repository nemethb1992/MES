

<div class='TM_backBtn btnstyle selectBackBtn col-12 px-0'><img src='${pageContext.request.contextPath}/Public/icons/backArrow.svg'/><p>Menu</p></div>
<div class=' col-2'>
	<div class='cont_header'>
		<div class='input-icon-holderDiv'><img class='icon-form search-icon-form WSSearchImg' src='${pageContext.request.contextPath}/Public/icons/searchSignWhite.svg'><input class='ts_searchInp WSSearchIn' value=''><div class='refresh_btn'></div></div>
	</div>
	<div class='tmts_stationContainer row px-3'>
		<div class='tmts_stationBtnDivCont col px-0' value='Rose/Bopla' OnClick='PC_Select(this)'>
			<input disabled class='si1'value='Rose/Bopla'>
		</div>
		<div class='tmts_stationBtnDivCont col px-0' value='Rose/Bopla' OnClick='PC_Select(this)'>
			<input disabled class='si1'value='Rose/Bopla'>
		</div>
		<div class='tmts_stationBtnDivCont col px-0' value='Rose/Bopla' OnClick='PC_Select(this)'>
			<input disabled class='si1'value='Rose/Bopla'>
		</div>
		<div class='tmts_stationBtnDivCont col px-0' value='Rose/Bopla' OnClick='PC_Select(this)'>
			<input disabled class='si1'value='Rose/Bopla'>
		</div>
	</div>
</div>


<div class='sortContDiv col-10 pr-3 pl-0'>

	<div class='cont_header'>
		<div class='input-icon-holderDiv ts-loggedName-div'><img class='icon-form' src='${pageContext.request.contextPath}/Public/icons/user-shape-white.svg'><input disabled value=''></div>
	</div>
	<div class='sortStationListCont'>
		<div class='sortContDiv_nav'>		
			<div class='abas-list-head sort-list-head sort-list'>
						<div class='input-icon-holderDiv datepicker-50'><img class='icon-form search-icon-form AbasSearchImg' src='${pageContext.request.contextPath}/Public/icons/searchSign.svg'><input class='ts_searchInp AbasSearchIn' value=''></div>
			<div class='abasList-datepicker '>
					<input class='datepicker_own' type='date'/>
					<Button class='refresh_abaslist_btn'/>
		  </div>
			</div>
			
			<div class='ws-list-head sort-list-head sort-list'>
					<div class='input-icon-holderDiv stationName-holderDiv'><img class='icon-form' src='${pageContext.request.contextPath}/Public/icons/computerSignGray.svg'><input class='ts_wsNameInp station_label' value='Válasszon állomást'></div>
					<div class='input-icon-holderDiv sum-holderDiv'><img class='icon-form' src='${pageContext.request.contextPath}/Public/icons/sumSignGray.svg'><input class='ts_sumTime' value='0:00:00'></div>
			</div>
		</div>

		<div class='sortContDiv_ListHolder'>
			<div class='abas-list-holder sort-list-holder sort-list'>
				<div class='dnd-frame very-light-shadow dndf1 row'>

					<div class='dnd-container col-12 px-0' value='3'>
						<div class='container px-0'>
							<div class='row w-100 mx-auto'>
								<div class='col-5 dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
								</div>
								<div class='col-5 dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
								</div>
								<div class='col-2 dnd-input-div px-0'>
									<input class='h-100 w-100 task-panel-button' type='button'>
								</div>
							</div>
						</div>
					</div>




					<div class='dnd-container' OnClick='TaskSizeSwitch(this)' value='3'>
						<div class='icon-form dnd-icon pass-item'></div>
						<div class='dnd-input-container'>
							<div class='dnd-upper'>
								<div class='dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in1' value='input value'>
								</div>
								<div class='dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in2' value='input value'>
								</div>
								<div class='dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in3' value='input value'>
								</div>
								<div class='dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in4' value='input value'>
								</div>
								<div class='dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in5' value='input value'>
								</div>
								<div class='dnd-input-div'>
									<p>Input neve</p>
									<input disabled class='dnd-input dnd-in6' value='input value'>
								</div>
							</div>

						</div>
					</div>

				</div>
			</div>
			<div class='ws-list-holder sort-list-holder sort-list'>
				<div class='dnd-frame very-light-shadow dndf2'>



					<%-- 				<div class='dnd-container'><img class='icon-form dnd-icon' src='${pageContext.request.contextPath}/Public/icons/listRowsGray.svg'> --%>
					<!-- 					<div class='dnd-input-container'> -->
					<!-- 						<div class='dnd-upper'> -->
					<!-- 							<div class='dnd-input-div'> -->
					<!-- 								<p>Input neve</p> -->
					<!-- 								<input disabled class='dnd-input dnd-in1' value='input value'> -->
					<!-- 							</div> -->
					<!-- 							<div class='dnd-input-div'> -->
					<!-- 								<p>Input neve</p> -->
					<!-- 								<input disabled class='dnd-input dnd-in2' value='input value'> -->
					<!-- 							</div> -->
					<!-- 							<div class='dnd-input-div'> -->
					<!-- 								<p>Input neve</p> -->
					<!-- 								<input disabled class='dnd-input dnd-in3' value='input value'> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 						<div class='dnd-downer'> -->
					<!-- 							<div class='dnd-input-div'> -->
					<!-- 								<p>Input neve</p> -->
					<!-- 								<input disabled class='dnd-input dnd-in4' value='input value'> -->
					<!-- 							</div> -->
					<!-- 							<div class='dnd-input-div'> -->
					<!-- 								<p>Input neve</p> -->
					<!-- 								<input disabled class='dnd-input dnd-in5' value='input value'> -->
					<!-- 							</div> -->
					<!-- 							<div class='dnd-input-div'> -->
					<!-- 								<p>Input neve</p> -->
					<!-- 								<input disabled class='dnd-input dnd-in6' value='input value'> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 				</div> -->




				</div>
			</div>
		</div>
	</div>
</div>


