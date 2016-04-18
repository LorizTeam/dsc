      <%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
      <ul class="legend" style="list-style-type: square; float: right ">
			<li style="color:#66cc00;">รายได้</li>			
			<li style="color:#F7464A;">รายจ่าย</li>
			<li style="color:#ffd11a;">คงเหลือ</li>
		</ul>	
       <canvas id="PolarArea" height="310" width="500"></canvas>
        <script>

			
			var canvas_PolarArea = document.getElementById('PolarArea');
			
			var randomScalingFactor = function() {
			  return Math.round(Math.random() * 100)
			};
			var PolarAreaChartData = [
									{	label: "รายได้",
								        value: randomScalingFactor(),
								        color:"#66cc00",
								        highlight: "#73e600"
									},
									{	label: "รายจ่าย",
										value: randomScalingFactor(),
								        color:"#F7464A",
								        highlight: "#FF5A5E"
									},
									{	label: "คงเหลือ",
										value: randomScalingFactor(),
								        color:"#ffd11a",
								        highlight: "#ffdb4d"
									}
								]
				var PolarArea = new Chart(canvas_PolarArea.getContext('2d')).PolarArea(PolarAreaChartData, {
				  responsive:true,
				  barValueSpacing : 10,
				  barDatasetSpacing : 0
				});
	</script> 