      <%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
      <ul class="legend" style="list-style-type: square; float: right ">
			<li style="color:rgba(0, 102, 255,1);">งบประมาณ</li>			
			<li style="color:rgba(153, 0, 255,1);">รายจ่าย</li>
			<li style="color:rgba(0, 204, 0,1);">คงเหลือ</li>
			<li style="color:rgba(255, 102, 0,1);">รายรับ</li>
		</ul>	
       <canvas id="bar" height="310" width="1000"></canvas>
        <script>

			
			var canvas_bar = document.getElementById('bar');
			
			var randomScalingFactor = function() {
			  return Math.round(Math.random() * 100)
			};
			var barChartData = {
			   labels : ["January","February","March","April","May","June","July","August","September","October","December"],
			   datasets : [
							{	label: "My First dataset",						
								fillColor : "rgba(0, 102, 255,0.5)",
								strokeColor : "rgba(0, 102, 255,0.8)",
								highlightFill: "rgba(0, 102, 255,0.75)",
								highlightStroke: "rgba(0, 102, 255,1)",
								data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
							},
							{	label: "My First dataset2",
								fillColor : "rgba(153, 0, 255,0.5)",
								strokeColor : "rgba(153, 0, 255,0.8)",
								highlightFill : "rgba(153, 0, 255,0.75)",
								highlightStroke : "rgba(153, 0, 255,1)",
								data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
							},
							{	label: "My First dataset3",
								fillColor : "rgba(0, 204, 0,0.5)",
								strokeColor : "rgba(0, 204, 0,0.8)",
								highlightFill : "rgba(0, 204, 0,0.75)",
								highlightStroke : "rgba(0, 204, 0,1)",
								data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
							},
							{	label: "My First dataset4",
								fillColor : "rgba(255, 102, 0,0.5)",
								strokeColor : "rgba(255, 102, 0,0.8)",
								highlightFill : "rgba(255, 102, 0,0.75)",
								highlightStroke : "rgba(255, 102, 0,1)",
								data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
							}
							
						]
			
			  }
			var bar = new Chart(canvas_bar.getContext('2d')).Bar(barChartData, {
				  responsive:true,
				  barValueSpacing : 10,
				  barDatasetSpacing : 0
				});
	</script>