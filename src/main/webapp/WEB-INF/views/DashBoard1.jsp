<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" href="images/favicon.ico" type="image/ico" />

    <title>코칭 대시보드  </title>

    <!-- Bootstrap -->
    <link href="../CSP/dashboard/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="../CSP/dashboard/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="../CSP/dashboard/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="../CSP/dashboard/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
	
    <!-- bootstrap-progressbar -->
    <link href="../CSP/dashboard/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="../CSP/dashboard/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="../CSP/dashboard/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <!--  <link href="../CSP/dashboard/build/css/custom.min.css" rel="stylesheet"> -->
    <link href="../CSP/dashboard/build/css/custom.css" rel="stylesheet">
  
  
  	<!-- 수술 평균값을 요청  -->
  	<script>
  	
  	$(document).ready(function(){
		OperInfo();
		//CheckLicense("${pageContext.request.remoteUser}");
		
		RecentScoreSix();
	});
  	
  	// 그래프로 그리기위한 점수를 서버에 요청하는 함수 
  	function OperInfo(content_type) 
	{
  		// 디폴트는 ENTSurgery로 세팅. 대시보드에 진입시점에 컨텐츠를 선택할 수 가 없다.
  		if(content_type == undefined){
  			content_type = "ENTSurgery";
  		}
  		
  		document.getElementById("content_name").innerHTML = content_type;
  		
  		var user_id = "${pageContext.request.remoteUser}";
  		var formData = {"id":user_id, "content":content_type}
  		
		$.ajax({
			type: "get",
			url: "/CSP/operinfo",
			data: formData,
			success: function(result){
				console.log("result=>" + result);
				//$("#listReply").html(result);
				
				$("#aver_oper_score").html(result.aver_score + "점");
				$("#aver_oper_time").html(result.aver_time + "분");
				$("#aver_oper_succ").html(result.aver_succ + "%");
				$("#notbad_more_cnt").html(result.notbad_more_cnt + "회");
				
				var diff_aver_score = result.aver_score - result.aver_score20;
				var diff_aver_time = result.aver_time - result.aver_time20;
				var diff_aver_succ = result.aver_succ - result.aver_succ20;
				var diff_notbad_more_cnt = result.notbad_more_cnt - result.notbad_more_cnt20;
				
				ArrowDeltaMakeHtml(diff_aver_score, "#aver_oper_score_delta");
				ArrowDeltaMakeHtml(diff_aver_time, "#aver_oper_time_delta");
				ArrowDeltaMakeHtml(diff_aver_succ, "#aver_oper_succ_delta");
				ArrowDeltaMakeHtml(diff_notbad_more_cnt, "#notbad_more_cnt_delta");
							
				RecentSurgeryGraph(user_id, content_type);
			},
			error :	function(request, status, error){
				console.log(request.responseText);
			}
		});	
	}
  	
  	// 해당 태그에 값이 마이너스이면 빨간색 화살표, 플러스이면 녹색 화살표.
  	function ArrowDeltaMakeHtml(vDelta, tagId)
  	{
  		var html;
  		
  		if(vDelta > 0){
			html = "<i class='green'>";
			html += "<i class='fa fa-sort-asc'></i>" + vDelta + " </i> 지난 10회 수술 대비</span>";
			$(tagId).append(html);	
		}
		else{
			html = "<i class='red'>";
			html += "<i class='fa fa-sort-desc'></i>" + vDelta + " </i> 지난 10회 수술 대비</span>";
			$(tagId).append(html);
		}
  	}
  	
  	function LicenseCheck(user_id)
  	{
  		var license_param = {"user_id": user_id};
		$.ajax({
			type: "get",
			url: "/CSP/checkLicense",
			data: license_param,
			success: function(result){
				//console.log(result);
			},
			error :	function(request, status, error){
				console.log(request.responseText);
			}
		});
  	}
  	
 	// 현재 선택된 수술콘텐츠에 가장 최신의 6개의 평가 받은 점수를 요청받기 위한 함수.
  	function RecentScoreSix(content_type)
  	{
  		if(content_type == undefined){
  			content_type = "ENTSurgery";
  		}
  		
  		var user_id = "${pageContext.request.remoteUser}";
  		var formData = {"content_type":content_type, "user_id":user_id};
  	
		$.ajax({
			type: "get",
			url: "/CSP/RecentScoreSix",
			data: formData,
			success: function(result){
				//console.log(result);
				OperCapEval(result);
				
				$.each(result, function(i, val){
					if(i > 4)
						return;
					
					document.getElementById("recent_comment_score" + i).innerHTML = val.score + " 점";
					document.getElementById("recent_comment_replyer" + i).innerHTML = val.replyer;
					document.getElementById("recent_comment_context" + i).innerHTML = val.replytext;
					document.getElementById("recent_comment_date" + i).innerHTML = new Date(val.regdate);
					
					if(val.isRead == 'y'){
						document.getElementById("recent_comment_icon" + i).style.display = "none";
					}
					else{
						document.getElementById("recent_comment_icon" + i).style.display = "block";
					}
					
					var bno = val.bno;
					var temp = bno / 10
					temp = Math.floor(temp);
					var cur_page = temp + 1;
					
					var str_link = "/CSP/CoachingBoard/CoachingBoardView?bno=" + bno + "&curPage=" + cur_page + "&searchOption=&keyword=";
					document.getElementById("recent_comment_context" + i).setAttribute("href", str_link);
				});
			},
			error :	function(request, status, error){
				console.log(request.responseText);
			}
		});
  	}
 	
 	// 도넛 그래프 내용을 채우는 함수
 	function OperCapEval(result)
 	{
 		var oper_cnt = result.length;
 		var very_good_cnt = 0;
 		var good_cnt = 0;
 		var not_bad_cnt = 0;
 		var bad_cnt = 0;
 		var very_bad_cnt = 0;
 		
 		$.each(result, function(i, val){
 			if(val.score > 90){
 				very_good_cnt++;
 			}
 			else if(val.score > 80 && val.score < 90){
 				good_cnt++;
 			}
 			else if(val.score > 70 && val.score < 80){
 				not_bad_cnt++;
 			}
 			else if(val.score > 60 && val.score < 70){
 				bad_cnt++;
 			}
 			else{
 				very_bad_cnt++;
 			}
 		});
 		
 		console.log(oper_cnt + "," + very_good_cnt + "," + good_cnt + "," + not_bad_cnt + "," + bad_cnt + "," + very_bad_cnt);
 		var very_good_ratio = Math.floor((very_good_cnt / oper_cnt) * 100);
 		var good_ratio = Math.floor((good_cnt / oper_cnt) * 100);
 		var not_bad_ratio = Math.floor((not_bad_cnt / oper_cnt) * 100);
 		var bad_ratio = Math.floor((bad_cnt / oper_cnt) * 100);
 		var very_bad_ratio = Math.floor((very_bad_cnt / oper_cnt) * 100);
 		
 		document.getElementById("very_good_eval").innerHTML = very_good_ratio + "%";
 		document.getElementById("good_eval").innerHTML = good_ratio + "%";
 		document.getElementById("not_bad_eval").innerHTML = not_bad_ratio + "%";
 		document.getElementById("bad_eval").innerHTML = bad_ratio + " %";
 		document.getElementById("very_bad_eval").innerHTML = very_bad_ratio + "%";
 		
 		update_chart_doughnut([very_good_ratio, good_ratio, not_bad_ratio, bad_ratio, very_bad_ratio]);
 	}
	</script>
  	
  
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="/CSP" class="site_title"><i class="fa fa-home"></i> <span>Main</span></a>
            </div>

            <div class="clearfix"></div> 

            <!-- menu profile quick info -->
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="../CSP/dashboard/production/images/user.png" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>안녕하세요,</span>
                <c:choose>
					<c:when test="${session_login != null}">
						<h2><c:out value="${pageContext.request.remoteUser}"/></h2>
					</c:when>
				</c:choose>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-file"></i> 실습 카테고리 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <!--  <li><a href="index.html">ENT수술</a></li> 
                      <li><a href="index2.html">고관절치환술</a></li>
                      <li><a href="index3.html">reservation2</a></li> -->
                      <li><a id="ENTSurgery" href="#" onclick="OperInfo('ENTSurgery');">TSA 수술</a></li>
                      <li><a id="HipSurgery" href="#" onclick="OperInfo('HipSurgery');">OS 수슬</a></li>
                      <li><a id="ENTSurgery" href="#" onclick="OperInfo('ENTSurgery');">기타 수술</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-edit"></i> 피드백 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      
                      <!--  <li><a href="/CSP/CoachingBoard/CoachingBoardList?id=${pageContext.request.remoteUser}">업로드 게시판</a></li> -->
                      <li><a href="/CSP/CoachingBoard/CoachingBoardListHome">업로드 게시판</a></li>	
                      	
                      <!--  <li><a href="form_advanced.html">Advanced Components</a></li>
                      <li><a href="form_validation.html">Form Validation</a></li>
                      <li><a href="form_wizards.html">Form Wizard</a></li>
                      <li><a href="form_upload.html">Form Upload</a></li>
                      <li><a href="form_buttons.html">Form Buttons</a></li> -->
                    </ul>
                  </li>
                  <li><a><i class="fa fa-desktop"></i> UI Elements <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="general_elements.html">General Elements</a></li>
                      <li><a href="media_gallery.html">Media Gallery</a></li>
                      <li><a href="typography.html">Typography</a></li>
                      <li><a href="icons.html">Icons</a></li>
                      <li><a href="glyphicons.html">Glyphicons</a></li>
                      <li><a href="widgets.html">Widgets</a></li>
                      <li><a href="invoice.html">Invoice</a></li>
                      <li><a href="inbox.html">Inbox</a></li>
                      <li><a href="calendar.html">Calendar</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-table"></i> Tables <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="tables.html">Tables</a></li>
                      <li><a href="tables_dynamic.html">Table Dynamic</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-bar-chart-o"></i> Data Presentation <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="chartjs.html">Chart JS</a></li>
                      <li><a href="chartjs2.html">Chart JS2</a></li>
                      <li><a href="morisjs.html">Moris JS</a></li>
                      <li><a href="echarts.html">ECharts</a></li>
                      <li><a href="other_charts.html">Other Charts</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-clone"></i>Layouts <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="fixed_sidebar.html">Fixed Sidebar</a></li>
                      <li><a href="fixed_footer.html">Fixed Footer</a></li>
                    </ul>
                  </li>
                </ul>
              </div>
              <div class="menu_section">
                <h3>Live On</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-bug"></i> Additional Pages <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="e_commerce.html">E-commerce</a></li>
                      <li><a href="projects.html">Projects</a></li>
                      <li><a href="project_detail.html">Project Detail</a></li>
                      <li><a href="contacts.html">Contacts</a></li>
                      <li><a href="profile.html">Profile</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-windows"></i> Extras <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="page_403.html">403 Error</a></li>
                      <li><a href="page_404.html">404 Error</a></li>
                      <li><a href="page_500.html">500 Error</a></li>
                      <li><a href="plain_page.html">Plain Page</a></li>
                      <li><a href="login.html">Login Page</a></li>
                      <li><a href="pricing_tables.html">Pricing Tables</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-sitemap"></i> Multilevel Menu <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                        <li><a href="#level1_1">Level One</a>
                        <li><a>Level One<span class="fa fa-chevron-down"></span></a>
                          <ul class="nav child_menu">
                            <li class="sub_menu"><a href="level2.html">Level Two</a>
                            </li>
                            <li><a href="#level2_1">Level Two</a>
                            </li>
                            <li><a href="#level2_2">Level Two</a>
                            </li>
                          </ul>
                        </li>
                        <li><a href="#level1_2">Level One</a>
                        </li>
                    </ul>
                  </li>                  
                  <li><a href="javascript:void(0)"><i class="fa fa-laptop"></i> Landing Page <span class="label label-success pull-right">Coming Soon</span></a></li>
                </ul>
              </div>

            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li role="presentation" class="dropdown">
                  <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-envelope-o"></i>
                    <span class="badge bg-green">6</span>
                  </a>
                  <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                    <li>
                      <a>
                        <span class="image"><img src="../CSP/dashboard/production/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="../CSP/dashboard/production/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="../CSP/dashboard/production/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <a>
                        <span class="image"><img src="../CSP/dashboard/production/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <div class="text-center">
                        <a>
                          <strong>See All Alerts</strong>
                          <i class="fa fa-angle-right"></i>
                        </a>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <!-- top tiles -->
          <div class="row tile_count">
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> 평균 수술 점수</span>
              <div class="count" id="aver_oper_score"></div>
              <span class="count_bottom" id="aver_oper_score_delta"></span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-clock-o"></i> 평균 수술 시간</span>
              <div class="count" id="aver_oper_time"></div>
              <span class="count_bottom" id="aver_oper_time_delta"></span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> 수술 성공률</span>
              <div class="count green" id="aver_oper_succ"></div>
              <span class="count_bottom" id="aver_oper_succ_delta"></span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> 보통 등급 이상 횟수</span>
              <div class="count" id="notbad_more_cnt"></div>
              <span class="count_bottom" id="notbad_more_cnt_delta"></span>
            </div>
          </div>
          
          <!-- /top tiles -->

          <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
              <div class="dashboard_graph">

                <div class="row x_title">
                  <div class="col-md-6">
                    <h3>실습 결과 점수 추세 그래프 - <small id="content_name">이비인후과 수술 점수</small></h3>
                  </div>
                  <div class="col-md-6">
                    <div id="reportrange" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
                      <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                      <span>December 30, 2014 - January 28, 2015</span> <b class="caret"></b>
                    </div>
                  </div>
                </div>

                <div class="col-md-9 col-sm-9 col-xs-12">
                  <div><canvus id="chart_plot_01" class="demo-placeholder"></canvus></div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-12 bg-white">
                  <div class="x_title">
                    <h2>데이타 미정 그래프</h2>
                    <div class="clearfix"></div>
                  </div>

                  <div class="col-md-12 col-sm-12 col-xs-6">
                    <div>
                      <p>A</p>
                      <div class="">
                        <div class="progress progress_sm" style="width: 76%;">
                          <div class="progress-bar bg-blue" role="progressbar" data-transitiongoal="80"></div>
                        </div>
                      </div>
                    </div>
                    <div>
                      <p>B</p>
                      <div class="">
                        <div class="progress progress_sm" style="width: 76%;">
                          <div class="progress-bar bg-green" role="progressbar" data-transitiongoal="60"></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12 col-sm-12 col-xs-6">
                    <div>
                      <p>C</p>
                      <div class="">
                        <div class="progress progress_sm" style="width: 76%;">
                          <div class="progress-bar bg-orange" role="progressbar" data-transitiongoal="40"></div>
                        </div>
                      </div>
                    </div>
                    <div>
                      <p>D</p>
                      <div class="">
                        <div class="progress progress_sm" style="width: 76%;">
                          <div class="progress-bar bg-green" role="progressbar" data-transitiongoal="50"></div>
                        </div>
                      </div>
                    </div>
                  </div>

                </div>

                <div class="clearfix"></div>
              </div>
            </div>

          </div>
          <br />

          <div class="row">

            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320">
                <div class="x_title">
                  <h2>수술 후유증 원인 통계</h2> 
                  
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                
                <div class="x_content">
                
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>상처감염</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 66%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span id="">123 회</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>

                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>과다출혈</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 45%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>53 회</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>절개 부주의</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 25%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>23 회</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>마무리 부주의</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 5%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>3 회</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>예상 시간 초과</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 2%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>1 회</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>

                </div>
              </div>
            </div>

            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320 overflow_hidden">
                <div class="x_title">
                  <h2>수술 능력 평가치 비율 </h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Settings 1</a>
                        </li>
                        <li><a href="#">Settings 2</a>
                        </li>
                      </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <table class="" style="width:100%">
                    <tr>
                      <th style="width:37%;">
                        <p>평가 그래프</p> 
                      </th>
                      <th>
                        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                          <p class="">평가 등급</p>
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                          <p class="">평가 점유율</p>
                        </div>
                      </th>
                    </tr>
                    <tr>
                      <td>
                        <canvas class="canvasDoughnut" height="140" width="140" style="margin: 15px 10px 10px 0"></canvas>
                      </td>
                      <td>
                        <table class="tile_info">
                          <tr>
                            <td>
                              <p><i class="fa fa-square blue"></i>매우 좋음 </p>
                            </td>
                            <td id="very_good_eval"></td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square green"></i>좋음 </p>
                            </td>
                            <td id="good_eval"></td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square purple"></i>보통 </p>
                            </td>
                            <td id="not_bad_eval"></td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square aero"></i>나쁨 </p>
                            </td>
                            <td id="bad_eval"></td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square red"></i>매우 나쁨 </p>
                            </td>
                            <td id="very_bad_eval"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </div>
              </div>
            </div>


            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320">
                <div class="x_title">
                  <h2>데이타 미정 챠트</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Settings 1</a>
                        </li>
                        <li><a href="#">Settings 2</a>
                        </li>
                      </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <div class="dashboard-widget-content">
                    <ul class="quick-list">
                      <li><i class="fa fa-calendar-o"></i><a href="#">Settings</a>
                      </li>
                      <li><i class="fa fa-bars"></i><a href="#">Subscription</a>
                      </li>
                      <li><i class="fa fa-bar-chart"></i><a href="#">Auto Renewal</a> </li>
                      <li><i class="fa fa-line-chart"></i><a href="#">Achievements</a>
                      </li>
                      <li><i class="fa fa-bar-chart"></i><a href="#">Auto Renewal</a> </li>
                      <li><i class="fa fa-line-chart"></i><a href="#">Achievements</a>
                      </li>
                      <li><i class="fa fa-area-chart"></i><a href="#">Logout</a>
                      </li>
                    </ul>

                    <div class="sidebar-widget">
                        <h4>Profile Completion</h4>
                        <canvas width="150" height="80" id="chart_gauge_01" class="" style="width: 160px; height: 100px;"></canvas>
                        <div class="goal-wrapper">
                          <span id="gauge-text" class="gauge-value pull-left">0</span>
                          <span class="gauge-value pull-left">%</span>
                          <span id="goal-text" class="goal-value pull-right">100%</span>
                        </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>


          <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel">
                <div class="x_title">
                  <h2>최근 평가받은 영상</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                      <i class="fa fa-wrench"></i></a>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Settings 1</a>
                        </li>
                        <li><a href="#">Settings 2</a>
                        </li>
                      </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <div class="dashboard-widget-content">

					<!-- 최근 평가받은 영상 1번째 -->
                    <ul class="list-unstyled timeline widget">
                      <li>
                        <div class="block">
                          <div class="block_content">
                          	<img src="./assets/img/new_icon.gif" id="recent_comment_icon0" class="photo" alt="" style="width:20%;" />
                            <h2 class="title" id="recent_comment_score0" style="color:blue;"></h2>
                            <div class="byline">
                               by <a id="recent_comment_replyer0"></a> 
                              <a id="recent_comment_date0" ></a>
                            </div>
                            <a class="excerpt" id="recent_comment_context0" href="#">
                            </a>
                          </div>
                        </div>
                      </li>
                      
                      <!-- 최근 평가받은 영상 2번째 -->
                      <li>
                        <div class="block">
                          <div class="block_content">
                          	<img src="./assets/img/new_icon.gif" id="recent_comment_icon1" class="photo" alt="" style="width:20%;" />
                            <h2 class="title" id="recent_comment_score1" style="color:blue;"></h2>
                            <div class="byline">
                               by <a id="recent_comment_replyer1"></a>
                              <a id="recent_comment_date1"></a>
                            </div>
                            <a class="excerpt" id="recent_comment_context1" href="#">
                            </a>
                          </div>
                        </div>
                      </li>
                      
                      <!-- 최근 평가받은 영상 3번째 -->
                      <li>
                        <div class="block">
                          <div class="block_content">
                          	<img src="./assets/img/new_icon.gif" id="recent_comment_icon2" class="photo" alt="" style="width:20%;" />
                            <h2 class="title" id="recent_comment_score2" style="color:blue;"></h2>
                            <div class="byline">
                              by <a id="recent_comment_replyer2"></a>
                              <a id="recent_comment_date2"></a>
                            </div>
                            <a class="excerpt" id="recent_comment_context2" href="#">
                            </a>
                          </div>
                        </div>
                      </li>
                      
                      <!-- 최근 평가받은 영상 4번째 -->
                      <li>
                        <div class="block">
                          <div class="block_content">
                          	<img src="./assets/img/new_icon.gif" id="recent_comment_icon3" class="photo" alt="" style="width:20%;" />
                            <h2 class="title" id="recent_comment_score3" style="color:blue;"></h2>
                            <div class="byline">
                               by <a id="recent_comment_replyer3"></a>
                              <a id="recent_comment_date3"></a>
                            </div>
                            <a class="excerpt" id="recent_comment_context3" href="#">
                            </a>
                          </div>
                        </div>
                      </li>
                      
                      <!-- 최근 평가받은 영상 5번째 -->
                      <li>
                        <div class="block">
                          <div class="block_content" >
                          	<img src="./assets/img/new_icon.gif" id="recent_comment_icon4" class="photo" alt="" style="width:20%;" />
                            <h2 class="title" id="recent_comment_score4" style="color:blue;"></h2>
                            <div class="byline">
                               by <a id="recent_comment_replyer4"></a>
                              <a id="recent_comment_date4"></a>
                            </div>
                            <a class="excerpt" id="recent_comment_context4" href="#">
                            </a>
                          </div>
                        </div>
                      </li>
                      
                      
                    </ul>
                  </div>
                </div>
              </div>
            </div>


            <div class="col-md-8 col-sm-8 col-xs-12">



              <div class="row">

                <div class="col-md-12 col-sm-12 col-xs-12">
                  <div class="x_panel">
                    <div class="x_title">
                      <h2>데이타 미정 챠트 <small>temporary</small></h2>
                      <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                          <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Settings 1</a>
                            </li>
                            <li><a href="#">Settings 2</a>
                            </li>
                          </ul>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                      </ul>
                      <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                      <div class="dashboard-widget-content">
                        <div class="col-md-4 hidden-small">
                          <h2 class="line_30">125.7k Views from 60 countries</h2>

                          <table class="countries_list">
                            <tbody>
                              <tr>
                                <td>United States</td>
                                <td class="fs15 fw700 text-right">33%</td>
                              </tr>
                              <tr>
                                <td>France</td>
                                <td class="fs15 fw700 text-right">27%</td>
                              </tr>
                              <tr>
                                <td>Germany</td>
                                <td class="fs15 fw700 text-right">16%</td>
                              </tr>
                              <tr>
                                <td>Spain</td>
                                <td class="fs15 fw700 text-right">11%</td>
                              </tr>
                              <tr>
                                <td>Britain</td>
                                <td class="fs15 fw700 text-right">10%</td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                        <div id="world-map-gdp" class="col-md-8 col-sm-12 col-xs-12" style="height:230px;"></div>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
              <div class="row">


                <!-- Start to do list -->
                <div class="col-md-6 col-sm-6 col-xs-12">
                  <div class="x_panel">
                    <div class="x_title">
                      <h2>데이타 미정 챠트 <small>temporary</small></h2>
                      <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                          <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Settings 1</a>
                            </li>
                            <li><a href="#">Settings 2</a>
                            </li>
                          </ul>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                      </ul>
                      <div class="clearfix"></div>
                    </div>
                    <div class="x_content">

                      <div class="">
                        <ul class="to_do">
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Schedule meeting with new client </p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Create email address for new intern</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Have IT fix the network printer</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Copy backups to offsite location</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Food truck fixie locavors mcsweeney</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Food truck fixie locavors mcsweeney</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Create email address for new intern</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Have IT fix the network printer</p>
                          </li>
                          <li>
                            <p>
                              <input type="checkbox" class="flat"> Copy backups to offsite location</p>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- End to do list -->
                
                <!-- start of weather widget -->
                <div class="col-md-6 col-sm-6 col-xs-12">
                  <div class="x_panel">
                    <div class="x_title">
                      <h2>데이타 미정 챠트 <small>temporary</small></h2>
                      <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                          <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Settings 1</a>
                            </li>
                            <li><a href="#">Settings 2</a>
                            </li>
                          </ul>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                      </ul>
                      <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                      <div class="row">
                        <div class="col-sm-12">
                          <div class="temperature"><b>Monday</b>, 07:30 AM
                            <span>F</span>
                            <span><b>C</b></span>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-4">
                          <div class="weather-icon">
                            <canvas height="84" width="84" id="partly-cloudy-day"></canvas>
                          </div>
                        </div>
                        <div class="col-sm-8">
                          <div class="weather-text">
                            <h2>Texas <br><i>Partly Cloudy Day</i></h2>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="weather-text pull-right">
                          <h3 class="degrees">23</h3>
                        </div>
                      </div>

                      <div class="clearfix"></div>

                      <div class="row weather-days">
                        <div class="col-sm-2">
                          <div class="daily-weather">
                            <h2 class="day">Mon</h2>
                            <h3 class="degrees">25</h3>
                            <canvas id="clear-day" width="32" height="32"></canvas>
                            <h5>15 <i>km/h</i></h5>
                          </div>
                        </div>
                        <div class="col-sm-2">
                          <div class="daily-weather">
                            <h2 class="day">Tue</h2>
                            <h3 class="degrees">25</h3>
                            <canvas height="32" width="32" id="rain"></canvas>
                            <h5>12 <i>km/h</i></h5>
                          </div>
                        </div>
                        <div class="col-sm-2">
                          <div class="daily-weather">
                            <h2 class="day">Wed</h2>
                            <h3 class="degrees">27</h3>
                            <canvas height="32" width="32" id="snow"></canvas>
                            <h5>14 <i>km/h</i></h5>
                          </div>
                        </div>
                        <div class="col-sm-2">
                          <div class="daily-weather">
                            <h2 class="day">Thu</h2>
                            <h3 class="degrees">28</h3>
                            <canvas height="32" width="32" id="sleet"></canvas>
                            <h5>15 <i>km/h</i></h5>
                          </div>
                        </div>
                        <div class="col-sm-2">
                          <div class="daily-weather">
                            <h2 class="day">Fri</h2>
                            <h3 class="degrees">28</h3>
                            <canvas height="32" width="32" id="wind"></canvas>
                            <h5>11 <i>km/h</i></h5>
                          </div>
                        </div>
                        <div class="col-sm-2">
                          <div class="daily-weather">
                            <h2 class="day">Sat</h2>
                            <h3 class="degrees">26</h3>
                            <canvas height="32" width="32" id="cloudy"></canvas>
                            <h5>10 <i>km/h</i></h5>
                          </div>
                        </div>
                        <div class="clearfix"></div>
                      </div>
                    </div>
                  </div>

                </div>
                <!-- end of weather widget -->
              </div>
            </div>
          </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="../CSP/dashboard/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="../CSP/dashboard/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="../CSP/dashboard/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="../CSP/dashboard/vendors/nprogress/nprogress.js"></script>
    <!-- Chart.js -->
    <script src="../CSP/dashboard/vendors/Chart.js/dist/Chart.min.js"></script>
    <!-- gauge.js -->
    <script src="../CSP/dashboard/vendors/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="../CSP/dashboard/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="../CSP/dashboard/vendors/iCheck/icheck.min.js"></script>
    <!-- Skycons -->
    <script src="../CSP/dashboard/vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="../CSP/dashboard/vendors/Flot/jquery.flot.js"></script>
    <script src="../CSP/dashboard/vendors/Flot/jquery.flot.pie.js"></script>
    <script src="../CSP/dashboard/vendors/Flot/jquery.flot.time.js"></script>
    <script src="../CSP/dashboard/vendors/Flot/jquery.flot.stack.js"></script>
    
    <!-- 현재 아래의 리사이즈 기능이 문제가 있음. 스크롤이 계속 늘어나는 현상 -->
    <!--  <script src="../CSP/dashboard/vendors/Flot/jquery.flot.resize.js"></script> -->
    
    
    <!-- Flot plugins -->
    <script src="../CSP/dashboard/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
    <script src="../CSP/dashboard/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
    <script src="../CSP/dashboard/vendors/flot.curvedlines/curvedLines.js"></script>
    <!-- DateJS -->
    <script src="../CSP/dashboard/vendors/DateJS/build/date.js"></script>
    <!-- JQVMap -->
    <script src="../CSP/dashboard/vendors/jqvmap/dist/jquery.vmap.js"></script>
    <script src="../CSP/dashboard/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
    <script src="../CSP/dashboard/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="../CSP/dashboard/vendors/moment/min/moment.min.js"></script>
    <script src="../CSP/dashboard/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

    <!-- Custom Theme Scripts -->
    <!--  <script src="../CSP/dashboard/build/js/custom.min.js"></script> -->
	<script src="../CSP/dashboard/build/js/custom.js"></script> 
	
		
	
  </body>
</html>
