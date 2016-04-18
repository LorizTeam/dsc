<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%> 
	<ul class="legend" style="list-style-type: square; float: right ">
		<li style="color:rgba(220,220,220,1);">งบประมาณ</li>			
		<li style="color:rgba(153, 0, 255,0.7);">รายจ่าย</li>
	</ul>	
 <canvas id="line" height="310" width="1000"></canvas>
 
 <script>
			var randomScalingFactor = function() {
				  return Math.round(Math.random() * 100)
			};
 			var canvas_line = document.getElementById('line');
			var LineChartData = {
					   labels : ["January","February","March","April","May","June","July","August","September","October","December"],
					   datasets : [
									 {

										fillColor : "rgba(220,220,220,0)",
							            strokeColor: "rgba(220,220,220,1)",
							            pointColor: "rgba(220,220,220,1)",
							            pointStrokeColor: "#fff",
							            pointHighlightFill: "#fff",
							            pointHighlightStroke: "rgba(220,220,220,1)",
							            data: [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
							        },
									{	
										fillColor : "rgba(153, 0, 255,0)",
										strokeColor : "rgba(153, 0, 255,0.7)",
										pointColor : "rgba(153, 0, 255,0.7)",
										pointStrokeColor : "rgba(153, 0, 255,0.7)",
										pointStrokeColor : "rgba(153, 0, 255,0.7)",
							            pointHighlightStroke: "rgba(153, 0, 255,0.7)",
										data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
									}
									
								]
					
					  }
			 
			var line = new Chart(canvas_line.getContext('2d')).Line(LineChartData, {
				  responsive:true,
				  barValueSpacing : 10,
				  barDatasetSpacing : 0
			});
		
		</script>